API 0.2.0
-
- Breaking Changes
  - `Loader.getLogger()` now returns a sfl4j logger instead of a log4j one
  - Moved common `impl` into its own module
- Non-breaking Changes
  - Added an event system
  - Added chat client/server events: `lc.server.chat.receive`, `lc.client.chat.send`, `lc.client.chat.receive` events
  - Added server command registration event: `lc.server.command.register`
