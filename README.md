# java-toolkit

A lean, production-oriented "Swiss-army knife" of small, focused Java utilities.

- **Java 21**, built with Maven
- **Dependency-injection friendly** – collaborators (e.g. `OkHttpClient`) are injected, never created via hidden static state
- **No hidden side effects** – utilities throw meaningful exceptions instead of printing stack traces or writing to `System.out`
- **Tested** – JUnit 6, with a JaCoCo line-coverage gate of **85%** on production code (currently ~94%)
- **Quality-gated** – every build runs Spotless (google-java-format), Error Prone + NullAway, and SpotBugs

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
| JUnit 6 + JaCoCo | Tests + **85%** line-coverage gate (`verify`) | production code (scratch/HTML→PDF/signing excluded) |
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

### Notes on "niche" modules

`pdf.HTMLToPDF` and `pdf.sign` are retained and hardened but require
external resources (fonts/keystores) to exercise, so
they are excluded from the CI coverage gate. Everything else is unit-tested
(e.g. `pdf.ExtractAttachments`, `excel.EmbeddedFileExtractor`, `digitalsign.GenerateKeys`).

## `scratch` package

`com.rsh.jtoolkit.scratch.*` holds former demo / learning / coding-challenge classes (things that
were only `public static void main` experiments or hardcoded one-offs). They are **kept for review,
not part of the public API**, and are excluded from the coverage gate. Delete the ones you don't
want, or promote a class into a real module by giving it a clean, injectable API and tests.

## License

Licensed under the Apache License 2.0 — see the [LICENSE](LICENSE) file.
