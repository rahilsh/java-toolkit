# Changelog

All notable changes to this project are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- Community & contribution docs: `CONTRIBUTING.md`, `CODE_OF_CONDUCT.md`, issue/PR templates.
- README badges and a "Feature dependencies" table describing which optional dependency each
  feature module requires.

### Changed
- **Slimmed the dependency footprint.** Feature-specific libraries (PDF, Excel, HTTP, CSV, XML,
  phone, JSON-Schema) are now declared `<optional>true</optional>`, so they are no longer pulled
  transitively onto consumers' classpaths. Add them explicitly only for the features you use.

### Removed
- Dropped now-unused dependencies that were only referenced by removed demo code: `guice`,
  `commons-configuration2`, `commons-codec`, `commons-lang3`, `slf4j-api`. Removed the unused
  Guice-based test helpers (`AbstractTest`, `InjectorExtension`).

### Fixed
- Corrected the project `<url>` and added `<scm>` / `<issueManagement>` metadata.

<!--
## [1.0.0] - YYYY-MM-DD
### Added
- Initial public release.
-->

[Unreleased]: https://github.com/rahilsh/java-toolkit/commits/main
