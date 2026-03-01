import CoreLocation
import Foundation
import FluffBuzzKit
import UIKit

typealias FluffBuzzCameraSnapResult = (format: String, base64: String, width: Int, height: Int)
typealias FluffBuzzCameraClipResult = (format: String, base64: String, durationMs: Int, hasAudio: Bool)

protocol CameraServicing: Sendable {
    func listDevices() async -> [CameraController.CameraDeviceInfo]
    func snap(params: FluffBuzzCameraSnapParams) async throws -> FluffBuzzCameraSnapResult
    func clip(params: FluffBuzzCameraClipParams) async throws -> FluffBuzzCameraClipResult
}

protocol ScreenRecordingServicing: Sendable {
    func record(
        screenIndex: Int?,
        durationMs: Int?,
        fps: Double?,
        includeAudio: Bool?,
        outPath: String?) async throws -> String
}

@MainActor
protocol LocationServicing: Sendable {
    func authorizationStatus() -> CLAuthorizationStatus
    func accuracyAuthorization() -> CLAccuracyAuthorization
    func ensureAuthorization(mode: FluffBuzzLocationMode) async -> CLAuthorizationStatus
    func currentLocation(
        params: FluffBuzzLocationGetParams,
        desiredAccuracy: FluffBuzzLocationAccuracy,
        maxAgeMs: Int?,
        timeoutMs: Int?) async throws -> CLLocation
    func startLocationUpdates(
        desiredAccuracy: FluffBuzzLocationAccuracy,
        significantChangesOnly: Bool) -> AsyncStream<CLLocation>
    func stopLocationUpdates()
    func startMonitoringSignificantLocationChanges(onUpdate: @escaping @Sendable (CLLocation) -> Void)
    func stopMonitoringSignificantLocationChanges()
}

protocol DeviceStatusServicing: Sendable {
    func status() async throws -> FluffBuzzDeviceStatusPayload
    func info() -> FluffBuzzDeviceInfoPayload
}

protocol PhotosServicing: Sendable {
    func latest(params: FluffBuzzPhotosLatestParams) async throws -> FluffBuzzPhotosLatestPayload
}

protocol ContactsServicing: Sendable {
    func search(params: FluffBuzzContactsSearchParams) async throws -> FluffBuzzContactsSearchPayload
    func add(params: FluffBuzzContactsAddParams) async throws -> FluffBuzzContactsAddPayload
}

protocol CalendarServicing: Sendable {
    func events(params: FluffBuzzCalendarEventsParams) async throws -> FluffBuzzCalendarEventsPayload
    func add(params: FluffBuzzCalendarAddParams) async throws -> FluffBuzzCalendarAddPayload
}

protocol RemindersServicing: Sendable {
    func list(params: FluffBuzzRemindersListParams) async throws -> FluffBuzzRemindersListPayload
    func add(params: FluffBuzzRemindersAddParams) async throws -> FluffBuzzRemindersAddPayload
}

protocol MotionServicing: Sendable {
    func activities(params: FluffBuzzMotionActivityParams) async throws -> FluffBuzzMotionActivityPayload
    func pedometer(params: FluffBuzzPedometerParams) async throws -> FluffBuzzPedometerPayload
}

struct WatchMessagingStatus: Sendable, Equatable {
    var supported: Bool
    var paired: Bool
    var appInstalled: Bool
    var reachable: Bool
    var activationState: String
}

struct WatchQuickReplyEvent: Sendable, Equatable {
    var replyId: String
    var promptId: String
    var actionId: String
    var actionLabel: String?
    var sessionKey: String?
    var note: String?
    var sentAtMs: Int?
    var transport: String
}

struct WatchNotificationSendResult: Sendable, Equatable {
    var deliveredImmediately: Bool
    var queuedForDelivery: Bool
    var transport: String
}

protocol WatchMessagingServicing: AnyObject, Sendable {
    func status() async -> WatchMessagingStatus
    func setReplyHandler(_ handler: (@Sendable (WatchQuickReplyEvent) -> Void)?)
    func sendNotification(
        id: String,
        params: FluffBuzzWatchNotifyParams) async throws -> WatchNotificationSendResult
}

extension CameraController: CameraServicing {}
extension ScreenRecordService: ScreenRecordingServicing {}
extension LocationService: LocationServicing {}
