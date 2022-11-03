API 0.2.0
-
- Breaking Changes
  - `Loader.getLogger()` now returns a sfl4j logger instead of a log4j one
  - Separated packages into API and IMPL
- Non-breaking Changes
  - Added an event system
  - Added chat events: `lc.server.chat.receive`, `lc.client.chat.send`, `lc.client.chat.receive` events
  - Added server events: `lc.server.command.register`