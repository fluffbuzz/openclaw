import Foundation

public enum FluffBuzzDeviceCommand: String, Codable, Sendable {
    case status = "device.status"
    case info = "device.info"
}

public enum FluffBuzzBatteryState: String, Codable, Sendable {
    case unknown
    case unplugged
    case charging
    case full
}

public enum FluffBuzzThermalState: String, Codable, Sendable {
    case nominal
    case fair
    case serious
    case critical
}

public enum FluffBuzzNetworkPathStatus: String, Codable, Sendable {
    case satisfied
    case unsatisfied
    case requiresConnection
}

public enum FluffBuzzNetworkInterfaceType: String, Codable, Sendable {
    case wifi
    case cellular
    case wired
    case other
}

public struct FluffBuzzBatteryStatusPayload: Codable, Sendable, Equatable {
    public var level: Double?
    public var state: FluffBuzzBatteryState
    public var lowPowerModeEnabled: Bool

    public init(level: Double?, state: FluffBuzzBatteryState, lowPowerModeEnabled: Bool) {
        self.level = level
        self.state = state
        self.lowPowerModeEnabled = lowPowerModeEnabled
    }
}

public struct FluffBuzzThermalStatusPayload: Codable, Sendable, Equatable {
    public var state: FluffBuzzThermalState

    public init(state: FluffBuzzThermalState) {
        self.state = state
    }
}

public struct FluffBuzzStorageStatusPayload: Codable, Sendable, Equatable {
    public var totalBytes: Int64
    public var freeBytes: Int64
    public var usedBytes: Int64

    public init(totalBytes: Int64, freeBytes: Int64, usedBytes: Int64) {
        self.totalBytes = totalBytes
        self.freeBytes = freeBytes
        self.usedBytes = usedBytes
    }
}

public struct FluffBuzzNetworkStatusPayload: Codable, Sendable, Equatable {
    public var status: FluffBuzzNetworkPathStatus
    public var isExpensive: Bool
    public var isConstrained: Bool
    public var interfaces: [FluffBuzzNetworkInterfaceType]

    public init(
        status: FluffBuzzNetworkPathStatus,
        isExpensive: Bool,
        isConstrained: Bool,
        interfaces: [FluffBuzzNetworkInterfaceType])
    {
        self.status = status
        self.isExpensive = isExpensive
        self.isConstrained = isConstrained
        self.interfaces = interfaces
    }
}

public struct FluffBuzzDeviceStatusPayload: Codable, Sendable, Equatable {
    public var battery: FluffBuzzBatteryStatusPayload
    public var thermal: FluffBuzzThermalStatusPayload
    public var storage: FluffBuzzStorageStatusPayload
    public var network: FluffBuzzNetworkStatusPayload
    public var uptimeSeconds: Double

    public init(
        battery: FluffBuzzBatteryStatusPayload,
        thermal: FluffBuzzThermalStatusPayload,
        storage: FluffBuzzStorageStatusPayload,
        network: FluffBuzzNetworkStatusPayload,
        uptimeSeconds: Double)
    {
        self.battery = battery
        self.thermal = thermal
        self.storage = storage
        self.network = network
        self.uptimeSeconds = uptimeSeconds
    }
}

public struct FluffBuzzDeviceInfoPayload: Codable, Sendable, Equatable {
    public var deviceName: String
    public var modelIdentifier: String
    public var systemName: String
    public var systemVersion: String
    public var appVersion: String
    public var appBuild: String
    public var locale: String

    public init(
        deviceName: String,
        modelIdentifier: String,
        systemName: String,
        systemVersion: String,
        appVersion: String,
        appBuild: String,
        locale: String)
    {
        self.deviceName = deviceName
        self.modelIdentifier = modelIdentifier
        self.systemName = systemName
        self.systemVersion = systemVersion
        self.appVersion = appVersion
        self.appBuild = appBuild
        self.locale = locale
    }
}
