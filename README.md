# java-toolkit

[![Java CI with Maven](https://github.com/rahilsh/java-toolkit/actions/workflows/maven.yml/badge.svg)](https://github.com/rahilsh/java-toolkit/actions/workflows/maven.yml)
[![License: Apache 2.0](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](LICENSE)
[![Java 21](https://img.shields.io/badge/Java-21-orange.svg)](https://adoptium.net/)
[![Coverage](https://img.shields.io/badge/coverage-%E2%89%A585%25-brightgreen.svg)](#quality-tooling)
[![PRs welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)](CONTRIBUTING.md)

A lean, production-oriented "Swiss-army knife" of small, focused Java utilities.

- **Java 21**, built with Maven
- **Dependency-injection friendly** – collaborators (e.g. `OkHttpClient`) are injected, never created via hidden static state
- **No hidden side effects** – utilities throw meaningful exceptions instead of printing stack traces or writing to `System.out`
- **Lean footprint** – heavy libraries (PDF, Excel, HTTP, …) are *optional*; you pull only what your features need
- **Tested** – JUnit 6, with a JaCoCo line-coverage gate of **85%** on production code (currently ~90%)
- **Quality-gated** – every build runs Spotless (google-java-format), Error Prone + NullAway, and SpotBugs

## Using it in your project

Add the dependency to your build. By default you only get the light **core** dependencies —
the heavier feature libraries (PDF, Excel, HTTP, …) are `optional` and are **not** pulled onto your
classpath. Opt in to a feature by adding its dependency (see [Feature dependencies](#feature-dependencies)).

**Maven**

```xml
<dependency>
  <groupId>in.rsh.jtoolkit</groupId>
  <artifactId>jtoolkit</artifactId>
  <version>1.0.0</version>
</dependency>
```

**Gradle**

```kotlin
implementation("in.rsh.jtoolkit:jtoolkit:1.0.0")
```

> Not yet on Maven Central. Until it is published you can consume it via
> [JitPack](https://jitpack.io/#rahilsh/java-toolkit) (`com.github.rahilsh:java-toolkit:<tag>`) or by
> building from source (`mvn install`).

## Requirements

- JDK 21+
- Maven 3.9+

## Build & test

```bash
mvn verify            # compile, tests, coverage gate, Spotless check, Error Prone, SpotBugs
mvn test              # run tests only
mvn spotless:apply    # auto-format the code
mvn -Psecurity verify # additionally run OWASP dependency-check (set NVD_API_KEY for speed)
```

The HTML coverage report is written to `target/site/jacoco/index.html`.

## Quality tooling

| Tool | Purpose | Scope |
|---|---|---|
| JUnit 6 + JaCoCo | Tests + **85%** line-coverage gate (`verify`) | all production code (only `scratch` excluded) |
| Spotless (google-java-format) | Formatting, import ordering, unused-import removal | production + tests (scratch excluded) |
| Error Prone + NullAway | Compile-time bug & nullability analysis | production + tests (scratch excluded; NullAway is a warning) |
| SpotBugs (High threshold) | Bytecode bug detection (`verify`) | production (scratch excluded — see `spotbugs-exclude.xml`) |
| OWASP dependency-check | Known-vulnerability scanning (opt-in `-Psecurity`) | all dependencies |

JDK 16+ requires the compiler exports in `.mvn/jvm.config` for google-java-format and Error Prone.


## Design principles

1. **Constructor injection, framework-agnostic.** Classes that need a collaborator take it
   through their constructor. Nothing in the library depends on a specific DI container, so it
   works with Spring, Guice, Dagger, or plain manual wiring.
2. **Stateless static helpers where it makes sense.** Pure functions (e.g. `ListUtil.min`) stay
   as `static` methods on a `final` class with a private constructor.
3. **Fail loudly, not silently.** I/O failures surface as `IOException` or `UncheckedIOException`
   with context, never swallowed.
4. **UTF-8 everywhere.** No reliance on the platform default charset.

### Dependency injection example

```java
// Build and share one OkHttpClient for the whole application.
OkHttpClient okHttp = new OkHttpClient.Builder()
    .connectTimeout(Duration.ofSeconds(10))
    .readTimeout(Duration.ofSeconds(30))
    .build();

HttpClient httpClient = new HttpClient(okHttp);   // inject it

try (Response response = httpClient.get("https://example.com/api", Map.of("Accept", "application/json"))) {
  System.out.println(response.code());
}
```

In tests, inject an `OkHttpClient` pointed at an OkHttp `MockWebServer` – no network required
(see `HttpClientTest`).

## Modules

Production utilities live under `com.rsh.jtoolkit.*`:

| Package | Class | Purpose |
|---|---|---|
| `clients` | `HttpClient` | DI-friendly wrapper over `OkHttpClient` (GET/POST/PUT/DELETE) |
| `collection` | `ListUtil`, `SetUtil` | `min`/`max`, delimited-string to `Set` |
| `csv` | `CSVUtil` | Map a classpath CSV onto beans (OpenCSV) |
| `digitalsign` | `GenerateKeys`, `SignatureUtil` | Generate RSA key pairs; RSA sign/verify (`SHA256withRSA`) |
| `email` | `EmailUtil` | Email syntax validation |
| `emoji` | `EmojiUtil` | Strip emoji / emoji modifiers from text |
| `excel` | `EmbeddedFileExtractor`, `EmbeddedFile` | Extract files embedded in `.xls`/`.xlsx` workbooks (POI) |
| `file` | `FileUtil` | Read/write files, bulk extension rename, folder rename |
| `future` | `FutureUtil` | Collect results of many `CompletionStage`s |
| `ip` | `IPUtil` | CIDR range checks and IPv4 validation |
| `json` | `JsonUtil`, `ReadJsonFile`, `JsonSchemaValidator` | Map⇄JSON, read JSON file, JSON-Schema validation |
| `lang` | `ObjectUtil` | `firstNonNull(...)` |
| `pdf` | `ExtractAttachments`, `PdfUtil`, `HTMLToPDF`, `sign.PDFSigner` | PDF attachments, PDF/A detection, HTML→PDF, digital signing |
| `phone` | `PhoneNumberUtil` | Phone-number validation (libphonenumber) |
| `primitive` | `ShortUtil` | Primitive helpers |
| `stream` | `StreamUtil` | Stream to file |
| `time` | `Time`, `DateUtil` | Microsecond `Timestamp` conversion; month-boundary helpers |
| `xml` | `XMLUtil` | XML ⇄ POJO (Jackson XML) |
| `zip` | `ZipUtil` | Zip a directory tree |

## Feature dependencies

To keep the library lean, the heavier libraries are declared **`optional`** and are **not** pulled
onto your classpath transitively. The core utilities (`collection`, `email`, `emoji`, `file`,
`future`, `ip`, `json` (Gson), `lang`, `primitive`, `stream`, `time`, `zip`, `digitalsign`) work out
of the box. If you use one of the feature modules below, add the matching dependency yourself:

| Feature (package) | Add this dependency |
|---|---|
| `xml` | `com.fasterxml.jackson.dataformat:jackson-dataformat-xml` |
| `csv` | `com.opencsv:opencsv` |
| `http` (`clients`) | `com.squareup.okhttp3:okhttp-jvm` |
| `phone` | `com.googlecode.libphonenumber:libphonenumber` |
| `json` – `JsonSchemaValidator` | `com.github.everit-org.json-schema:org.everit.json.schema` + `org.json:json` |
| `pdf` – attachments / PDF-A | `org.apache.pdfbox:pdfbox` |
| `pdf` – `HTMLToPDF` | `org.xhtmlrenderer:flying-saucer-pdf` |
| `pdf` – `PDFSigner` | `org.apache.pdfbox:pdfbox`, `org.bouncycastle:bcprov-jdk18on`, `org.bouncycastle:bcpkix-jdk18on` |
| `excel` | `org.apache.poi:poi`, `org.apache.poi:poi-ooxml`, `org.apache.poi:poi-ooxml-full` |

### Excluding features / dependencies you don't use

**Feature libraries are already opt-in.** Because they are declared `optional`, none of the PDF,
Excel, HTTP, CSV, XML, phone or JSON-Schema libraries are pulled transitively. If you don't use a
feature, simply don't add its dependency — there is nothing to exclude. (The feature *classes* still
live in the single jar, but they cost you nothing unless you call them and add their dependency.)

**Trimming the core dependencies.** The core utilities pull a handful of small libraries by default.
If you only use utilities that don't need a given core library, you can exclude it:

| Core dependency | Needed by | Safe to exclude if you don't use |
|---|---|---|
| `com.google.guava:guava` | `SetUtil`, `IPUtil` | those classes |
| `commons-io:commons-io` | `FileUtil`, `StreamUtil`, `CSVUtil` | those classes |
| `commons-net:commons-net` | `IPUtil` | `IPUtil` |
| `commons-validator:commons-validator` | `EmailUtil`, `IPUtil` | those classes |
| `com.google.code.gson:gson` | `JsonUtil`, `ReadJsonFile` | those classes |

**Maven** — add `<exclusions>` to the java-toolkit dependency:

```xml
<dependency>
  <groupId>in.rsh.jtoolkit</groupId>
  <artifactId>jtoolkit</artifactId>
  <version>1.0.0</version>
  <exclusions>
    <exclusion>
      <groupId>com.google.guava</groupId>
      <artifactId>guava</artifactId>
    </exclusion>
  </exclusions>
</dependency>
```

**Gradle**:

```kotlin
implementation("in.rsh.jtoolkit:jtoolkit:1.0.0") {
    exclude(group = "com.google.guava", module = "guava")
}
```

> ⚠️ Only exclude a dependency if you will not call the utilities that need it (see the table above).
> Calling a utility whose dependency you excluded will fail at runtime with `NoClassDefFoundError`.

### Notes on "niche" modules

Every production class is unit-tested, including the heavier PDF/Excel modules
(`pdf.HTMLToPDF`, `pdf.sign.PDFSigner`, `pdf.ExtractAttachments`, `pdf.PdfUtil`,
`excel.EmbeddedFileExtractor`, `digitalsign.GenerateKeys`/`SignatureUtil`). Tests generate their
own fixtures at runtime (in-memory keystores, workbooks and PDFs), so no external files are needed.

## Contributing

Contributions are welcome — see [CONTRIBUTING.md](CONTRIBUTING.md) and our
[Code of Conduct](CODE_OF_CONDUCT.md). Good first issues are labelled
[`good first issue`](https://github.com/rahilsh/java-toolkit/labels/good%20first%20issue).

## License

Licensed under the Apache License 2.0 — see the [LICENSE](LICENSE) file.
