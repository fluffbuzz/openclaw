import Foundation

final class CanvasFileWatcher: @unchecked Sendable, SimpleFileWatcherOwner {
    let watcher: SimpleFileWatcher

    init(url: URL, onChange: @escaping () -> Void) {
        self.watcher = SimpleFileWatcher(CoalescingFSEventsWatcher(
            paths: [url.path],
            queueLabel: "com.fluffbuzz.canvaswatcher",
            onChange: onChange))
    }
}
