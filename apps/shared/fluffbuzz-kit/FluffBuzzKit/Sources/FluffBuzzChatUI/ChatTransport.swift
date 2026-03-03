import Foundation

public enum FluffBuzzChatTransportEvent: Sendable {
    case health(ok: Bool)
    case tick
    case chat(FluffBuzzChatEventPayload)
    case agent(FluffBuzzAgentEventPayload)
    case seqGap
}

public protocol FluffBuzzChatTransport: Sendable {
    func requestHistory(sessionKey: String) async throws -> FluffBuzzChatHistoryPayload
    func sendMessage(
        sessionKey: String,
        message: String,
        thinking: String,
        idempotencyKey: String,
        attachments: [FluffBuzzChatAttachmentPayload]) async throws -> FluffBuzzChatSendResponse

    func abortRun(sessionKey: String, runId: String) async throws
    func listSessions(limit: Int?) async throws -> FluffBuzzChatSessionsListResponse

    func requestHealth(timeoutMs: Int) async throws -> Bool
    func events() -> AsyncStream<FluffBuzzChatTransportEvent>

    func setActiveSessionKey(_ sessionKey: String) async throws
}

extension FluffBuzzChatTransport {
    public func setActiveSessionKey(_: String) async throws {}

    public func abortRun(sessionKey _: String, runId _: String) async throws {
        throw NSError(
            domain: "FluffBuzzChatTransport",
            code: 0,
            userInfo: [NSLocalizedDescriptionKey: "chat.abort not supported by this transport"])
    }

    public func listSessions(limit _: Int?) async throws -> FluffBuzzChatSessionsListResponse {
        throw NSError(
            domain: "FluffBuzzChatTransport",
            code: 0,
            userInfo: [NSLocalizedDescriptionKey: "sessions.list not supported by this transport"])
    }
}
