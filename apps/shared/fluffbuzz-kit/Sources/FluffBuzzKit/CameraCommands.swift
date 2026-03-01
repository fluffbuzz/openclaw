import Foundation

public enum FluffBuzzCameraCommand: String, Codable, Sendable {
    case list = "camera.list"
    case snap = "camera.snap"
    case clip = "camera.clip"
}

public enum FluffBuzzCameraFacing: String, Codable, Sendable {
    case back
    case front
}

public enum FluffBuzzCameraImageFormat: String, Codable, Sendable {
    case jpg
    case jpeg
}

public enum FluffBuzzCameraVideoFormat: String, Codable, Sendable {
    case mp4
}

public struct FluffBuzzCameraSnapParams: Codable, Sendable, Equatable {
    public var facing: FluffBuzzCameraFacing?
    public var maxWidth: Int?
    public var quality: Double?
    public var format: FluffBuzzCameraImageFormat?
    public var deviceId: String?
    public var delayMs: Int?

    public init(
        facing: FluffBuzzCameraFacing? = nil,
        maxWidth: Int? = nil,
        quality: Double? = nil,
        format: FluffBuzzCameraImageFormat? = nil,
        deviceId: String? = nil,
        delayMs: Int? = nil)
    {
        self.facing = facing
        self.maxWidth = maxWidth
        self.quality = quality
        self.format = format
        self.deviceId = deviceId
        self.delayMs = delayMs
    }
}

public struct FluffBuzzCameraClipParams: Codable, Sendable, Equatable {
    public var facing: FluffBuzzCameraFacing?
    public var durationMs: Int?
    public var includeAudio: Bool?
    public var format: FluffBuzzCameraVideoFormat?
    public var deviceId: String?

    public init(
        facing: FluffBuzzCameraFacing? = nil,
        durationMs: Int? = nil,
        includeAudio: Bool? = nil,
        format: FluffBuzzCameraVideoFormat? = nil,
        deviceId: String? = nil)
    {
        self.facing = facing
        self.durationMs = durationMs
        self.includeAudio = includeAudio
        self.format = format
        self.deviceId = deviceId
    }
}
