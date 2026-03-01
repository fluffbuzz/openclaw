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
  }

  @Test
  fun screenCommandsUseStableStrings() {
    assertEquals("screen.record", FluffBuzzScreenCommand.Record.rawValue)
  }

  @Test
  fun notificationsCommandsUseStableStrings() {
    assertEquals("notifications.list", FluffBuzzNotificationsCommand.List.rawValue)
  }

  @Test
  fun deviceCommandsUseStableStrings() {
    assertEquals("device.status", FluffBuzzDeviceCommand.Status.rawValue)
    assertEquals("device.info", FluffBuzzDeviceCommand.Info.rawValue)
  }
}
