# VersatiaExample-Text-ServerPresentations

Two plugins: a provider deciding how a player appears in the messages of every plugin, with a card on hover and a rank, and a consumer that knows nothing of it showing both.

Part of the [Versatia examples](https://github.com/VersatiaFramework/VersatiaExamples); documentation: [Presentations for the whole server](https://versatia.dev/docs/messages/arguments#server-presentations).

**Start with** [`provider/.../PlayerCardPresenter.kt`](provider/src/main/kotlin/dev/versatia/example/serverpresentations/PlayerCardPresenter.kt), then [`consumer/.../joined.yml`](consumer/src/main/resources/resources/messages/joined.yml).

## What the log shows

When a player joins, the consumer's join message, with the player's display name in the server's
`player` style and, in the game, the card on hover:

```
Steve joined, rank Newcomer.
```

and `rank Veteran` from the second join on.

## Things to try

- Hover over the name in the chat: the card is the provider's `card` message, `Steve, rank
  Newcomer`. Every plugin's `{player}` shows it, with no line of code in the consumer.
- Write `@ServerPresentation(style = "accent")`: the name takes the `accent` instead of the
  server's `player` style, which a presentation keeps until it names another.
- Remove the `component { ... }` block: `{player}` is the plain display name again, while
  `{player.rank}` stays, because a server presentation replaces only what it defines.
- Disable the provider with a plugin manager: `{player}` falls back to the built-in presentation,
  and the next join message fails with `{player} has no property "rank"`, since no presentation
  defines it any more.
- Copy `PlayerCardPresenter` (with `Ranks` and `card.yml`) into the consumer: the plugin starting
  second fails with `org.bukkit.entity.Player would have several server presentations: ...`. Mark
  one of the two `@Primary` and both start; the `@Primary` one is used.

## Building

Requires JDK 25 and VersatiaAPI and
VersatiaCatalog, which supplies every version, in your local Maven repository (`./gradlew publishToMavenLocal` in each checkout; see
[Building the framework](https://versatia.dev/docs/contributing/building));
running it requires a Paper 26.3 server with VersatiaCore installed.

```bash
./gradlew build
```

To have the JARs copied to your server after every build, copy `local.properties.example` to
`local.properties` and set `pluginsDir`. The generated descriptors are in
`*/build/generated/ksp/main/kotlin`; open the provider's to see the `Presenter<Player>` it records.

The Versatia version is `versatiaVersion` in `gradle.properties`; `settings.gradle.kts` imports
the matching [VersatiaCatalog](https://versatia.dev/docs/getting-started/installation#versatia-catalog),
so the build scripts write `libs.versatia.api` and `alias(libs.plugins.ksp)` instead of version
numbers.

## Layout

Two Gradle modules, one plugin each. The consumer neither compiles against the provider nor lists
it under `depend:`: it only writes `{player}` and `{player.rank}` in its message, and the server
decides what they show.
