# Contributing to java-toolkit

Thanks for your interest in contributing! This project is a small, focused collection of
production-ready Java utilities, and it's also meant to be a friendly place to learn. Contributions
of all sizes are welcome — bug fixes, new utilities, tests, and documentation.

## Ways to contribute

- **Report a bug** – open an issue using the *Bug report* template.
- **Request a utility/feature** – open an issue using the *Feature request* template.
- **Fix a "good first issue"** – these are scoped to be approachable for newcomers.
- **Improve docs or examples** – README, Javadoc, and usage examples are always welcome.

## Development setup

Requirements: **JDK 21+** and **Maven 3.9+**.

```bash
git clone https://github.com/rahilsh/java-toolkit.git
cd java-toolkit
mvn verify        # compile, test, coverage gate, Spotless, Error Prone, SpotBugs
```

Useful commands:

```bash
mvn test              # run tests only
mvn spotless:apply    # auto-format the code (run this before committing)
mvn -Psecurity verify # additionally run OWASP dependency-check
```

## Quality gates (must pass before a PR is merged)

Every build runs, and your PR must keep them green:

| Gate | What it enforces |
|---|---|
| JUnit 6 + JaCoCo | All tests pass; **≥ 85%** line coverage on production code |
| Spotless (google-java-format) | Formatting & import order — run `mvn spotless:apply` |
| Error Prone + NullAway | Compile-time bug & nullability checks |
| SpotBugs (High) | Bytecode bug detection |

## Guidelines for new utilities

To keep the library consistent and dependency-light:

1. **Small and focused.** One clear responsibility per class.
2. **Framework-agnostic & injectable.** Take collaborators via the constructor; never hide global
   mutable state. Pure helpers are `static` on a `final` class with a private constructor.
3. **Fail loudly.** Throw meaningful exceptions (`IOException` / `UncheckedIOException` with
   context); never swallow errors or print stack traces.
4. **UTF-8 everywhere.** Never rely on the platform default charset.
5. **Mind the dependencies.** Core utilities should avoid heavy dependencies. Anything that needs a
   heavy library (PDF, Excel, HTTP, etc.) must declare that dependency as `<optional>true</optional>`
   and be documented in the README "Feature dependencies" table.
6. **Test it.** Add JUnit 6 tests, including edge cases. Generate fixtures at runtime where possible
   (no committed binaries).

## Pull request process

1. Fork the repo and create a topic branch (`feat/...`, `fix/...`, `docs/...`).
2. Make your change, add tests, and run `mvn spotless:apply && mvn verify` locally.
3. Update the README and `CHANGELOG.md` (`Unreleased` section) if your change is user-facing.
4. Open a PR using the template and link any related issue.
5. Keep PRs focused; unrelated changes should go in separate PRs.

## Commit messages

Short, imperative summaries are appreciated (Conventional Commits style is welcome but not
required), e.g. `fix(csv): support OpenCSV 5.x builder API`.

## Code of Conduct

By participating, you agree to abide by our [Code of Conduct](CODE_OF_CONDUCT.md).

## License

By contributing, you agree that your contributions will be licensed under the
[Apache License 2.0](LICENSE).
