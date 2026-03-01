package com.fluffbuzz.android.node

import com.fluffbuzz.android.protocol.FluffBuzzCameraCommand
import com.fluffbuzz.android.protocol.FluffBuzzDeviceCommand
import com.fluffbuzz.android.protocol.FluffBuzzLocationCommand
import com.fluffbuzz.android.protocol.FluffBuzzNotificationsCommand
import com.fluffbuzz.android.protocol.FluffBuzzSmsCommand
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class InvokeCommandRegistryTest {
  @Test
  fun advertisedCommands_respectsFeatureAvailability() {
    val commands =
      InvokeCommandRegistry.advertisedCommands(
        cameraEnabled = false,
        locationEnabled = false,
        smsAvailable = false,
        debugBuild = false,
      )

    assertFalse(commands.contains(FluffBuzzCameraCommand.Snap.rawValue))
    assertFalse(commands.contains(FluffBuzzCameraCommand.Clip.rawValue))
    assertFalse(commands.contains(FluffBuzzLocationCommand.Get.rawValue))
    assertTrue(commands.contains(FluffBuzzDeviceCommand.Status.rawValue))
    assertTrue(commands.contains(FluffBuzzDeviceCommand.Info.rawValue))
    assertTrue(commands.contains(FluffBuzzNotificationsCommand.List.rawValue))
    assertFalse(commands.contains(FluffBuzzSmsCommand.Send.rawValue))
    assertFalse(commands.contains("debug.logs"))
    assertFalse(commands.contains("debug.ed25519"))
    assertTrue(commands.contains("app.update"))
  }

  @Test
  fun advertisedCommands_includesFeatureCommandsWhenEnabled() {
    val commands =
      InvokeCommandRegistry.advertisedCommands(
        cameraEnabled = true,
        locationEnabled = true,
        smsAvailable = true,
        debugBuild = true,
      )

    assertTrue(commands.contains(FluffBuzzCameraCommand.Snap.rawValue))
    assertTrue(commands.contains(FluffBuzzCameraCommand.Clip.rawValue))
    assertTrue(commands.contains(FluffBuzzLocationCommand.Get.rawValue))
    assertTrue(commands.contains(FluffBuzzDeviceCommand.Status.rawValue))
    assertTrue(commands.contains(FluffBuzzDeviceCommand.Info.rawValue))
    assertTrue(commands.contains(FluffBuzzNotificationsCommand.List.rawValue))
    assertTrue(commands.contains(FluffBuzzSmsCommand.Send.rawValue))
    assertTrue(commands.contains("debug.logs"))
    assertTrue(commands.contains("debug.ed25519"))
    assertTrue(commands.contains("app.update"))
  }
}
