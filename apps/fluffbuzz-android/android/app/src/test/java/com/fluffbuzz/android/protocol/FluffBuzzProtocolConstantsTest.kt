package com.fluffbuzz.android.protocol

import org.junit.Assert.assertEquals
import org.junit.Test

class FluffBuzzProtocolConstantsTest {
  @Test
  fun canvasCommandsUseStableStrings() {
    assertEquals("canvas.present", FluffBuzzCanvasCommand.Present.rawValue)
    assertEquals("canvas.hide", FluffBuzzCanvasCommand.Hide.rawValue)
    assertEquals("canvas.navigate", FluffBuzzCanvasCommand.Navigate.rawValue)
    assertEquals("canvas.eval", FluffBuzzCanvasCommand.Eval.rawValue)
    assertEquals("canvas.snapshot", FluffBuzzCanvasCommand.Snapshot.rawValue)
  }

  @Test
  fun a2uiCommandsUseStableStrings() {
    assertEquals("canvas.a2ui.push", FluffBuzzCanvasA2UICommand.Push.rawValue)
    assertEquals("canvas.a2ui.pushJSONL", FluffBuzzCanvasA2UICommand.PushJSONL.rawValue)
    assertEquals("canvas.a2ui.reset", FluffBuzzCanvasA2UICommand.Reset.rawValue)
  }

  @Test
  fun capabilitiesUseStableStrings() {
    assertEquals("canvas", FluffBuzzCapability.Canvas.rawValue)
    assertEquals("camera", FluffBuzzCapability.Camera.rawValue)
    assertEquals("screen", FluffBuzzCapability.Screen.rawValue)
    assertEquals("voiceWake", FluffBuzzCapability.VoiceWake.rawValue)
    assertEquals("location", FluffBuzzCapability.Location.rawValue)
    assertEquals("sms", FluffBuzzCapability.Sms.rawValue)
    assertEquals("device", FluffBuzzCapability.Device.rawValue)
    assertEquals("notifications", FluffBuzzCapability.Notifications.rawValue)
    assertEquals("system", FluffBuzzCapability.System.rawValue)
    assertEquals("appUpdate", FluffBuzzCapability.AppUpdate.rawValue)
    assertEquals("photos", FluffBuzzCapability.Photos.rawValue)
    assertEquals("contacts", FluffBuzzCapability.Contacts.rawValue)
    assertEquals("calendar", FluffBuzzCapability.Calendar.rawValue)
    assertEquals("motion", FluffBuzzCapability.Motion.rawValue)
  }

  @Test
  fun cameraCommandsUseStableStrings() {
    assertEquals("camera.list", FluffBuzzCameraCommand.List.rawValue)
    assertEquals("camera.snap", FluffBuzzCameraCommand.Snap.rawValue)
    assertEquals("camera.clip", FluffBuzzCameraCommand.Clip.rawValue)
  }

  @Test
  fun screenCommandsUseStableStrings() {
    assertEquals("screen.record", FluffBuzzScreenCommand.Record.rawValue)
  }

  @Test
  fun notificationsCommandsUseStableStrings() {
    assertEquals("notifications.list", FluffBuzzNotificationsCommand.List.rawValue)
    assertEquals("notifications.actions", FluffBuzzNotificationsCommand.Actions.rawValue)
  }

  @Test
  fun deviceCommandsUseStableStrings() {
    assertEquals("device.status", FluffBuzzDeviceCommand.Status.rawValue)
    assertEquals("device.info", FluffBuzzDeviceCommand.Info.rawValue)
    assertEquals("device.permissions", FluffBuzzDeviceCommand.Permissions.rawValue)
    assertEquals("device.health", FluffBuzzDeviceCommand.Health.rawValue)
  }

  @Test
  fun systemCommandsUseStableStrings() {
    assertEquals("system.notify", FluffBuzzSystemCommand.Notify.rawValue)
  }

  @Test
  fun photosCommandsUseStableStrings() {
    assertEquals("photos.latest", FluffBuzzPhotosCommand.Latest.rawValue)
  }

  @Test
  fun contactsCommandsUseStableStrings() {
    assertEquals("contacts.search", FluffBuzzContactsCommand.Search.rawValue)
    assertEquals("contacts.add", FluffBuzzContactsCommand.Add.rawValue)
  }

  @Test
  fun calendarCommandsUseStableStrings() {
    assertEquals("calendar.events", FluffBuzzCalendarCommand.Events.rawValue)
    assertEquals("calendar.add", FluffBuzzCalendarCommand.Add.rawValue)
  }

  @Test
  fun motionCommandsUseStableStrings() {
    assertEquals("motion.activity", FluffBuzzMotionCommand.Activity.rawValue)
    assertEquals("motion.pedometer", FluffBuzzMotionCommand.Pedometer.rawValue)
  }
}
