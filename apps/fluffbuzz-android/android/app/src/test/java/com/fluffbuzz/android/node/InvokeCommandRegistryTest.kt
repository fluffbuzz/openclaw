package com.fluffbuzz.android.node

import com.fluffbuzz.android.protocol.FluffBuzzCalendarCommand
import com.fluffbuzz.android.protocol.FluffBuzzCameraCommand
import com.fluffbuzz.android.protocol.FluffBuzzCapability
import com.fluffbuzz.android.protocol.FluffBuzzContactsCommand
import com.fluffbuzz.android.protocol.FluffBuzzDeviceCommand
import com.fluffbuzz.android.protocol.FluffBuzzLocationCommand
import com.fluffbuzz.android.protocol.FluffBuzzMotionCommand
import com.fluffbuzz.android.protocol.FluffBuzzNotificationsCommand
import com.fluffbuzz.android.protocol.FluffBuzzPhotosCommand
import com.fluffbuzz.android.protocol.FluffBuzzSmsCommand
import com.fluffbuzz.android.protocol.FluffBuzzSystemCommand
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class InvokeCommandRegistryTest {
  private val coreCapabilities =
    setOf(
      FluffBuzzCapability.Canvas.rawValue,
      FluffBuzzCapability.Screen.rawValue,
      FluffBuzzCapability.Device.rawValue,
      FluffBuzzCapability.Notifications.rawValue,
      FluffBuzzCapability.System.rawValue,
      FluffBuzzCapability.AppUpdate.rawValue,
      FluffBuzzCapability.Photos.rawValue,
      FluffBuzzCapability.Contacts.rawValue,
      FluffBuzzCapability.Calendar.rawValue,
    )

  private val optionalCapabilities =
    setOf(
      FluffBuzzCapability.Camera.rawValue,
      FluffBuzzCapability.Location.rawValue,
      FluffBuzzCapability.Sms.rawValue,
      FluffBuzzCapability.VoiceWake.rawValue,
      FluffBuzzCapability.Motion.rawValue,
    )

  private val coreCommands =
    setOf(
      FluffBuzzDeviceCommand.Status.rawValue,
      FluffBuzzDeviceCommand.Info.rawValue,
      FluffBuzzDeviceCommand.Permissions.rawValue,
      FluffBuzzDeviceCommand.Health.rawValue,
      FluffBuzzNotificationsCommand.List.rawValue,
      FluffBuzzNotificationsCommand.Actions.rawValue,
      FluffBuzzSystemCommand.Notify.rawValue,
      FluffBuzzPhotosCommand.Latest.rawValue,
      FluffBuzzContactsCommand.Search.rawValue,
      FluffBuzzContactsCommand.Add.rawValue,
      FluffBuzzCalendarCommand.Events.rawValue,
      FluffBuzzCalendarCommand.Add.rawValue,
      "app.update",
    )

  private val optionalCommands =
    setOf(
      FluffBuzzCameraCommand.Snap.rawValue,
      FluffBuzzCameraCommand.Clip.rawValue,
      FluffBuzzCameraCommand.List.rawValue,
      FluffBuzzLocationCommand.Get.rawValue,
      FluffBuzzMotionCommand.Activity.rawValue,
      FluffBuzzMotionCommand.Pedometer.rawValue,
      FluffBuzzSmsCommand.Send.rawValue,
    )

  private val debugCommands = setOf("debug.logs", "debug.ed25519")

  @Test
  fun advertisedCapabilities_respectsFeatureAvailability() {
    val capabilities = InvokeCommandRegistry.advertisedCapabilities(defaultFlags())

    assertContainsAll(capabilities, coreCapabilities)
    assertMissingAll(capabilities, optionalCapabilities)
  }

  @Test
  fun advertisedCapabilities_includesFeatureCapabilitiesWhenEnabled() {
    val capabilities =
      InvokeCommandRegistry.advertisedCapabilities(
        defaultFlags(
          cameraEnabled = true,
          locationEnabled = true,
          smsAvailable = true,
          voiceWakeEnabled = true,
          motionActivityAvailable = true,
          motionPedometerAvailable = true,
        ),
      )

    assertContainsAll(capabilities, coreCapabilities + optionalCapabilities)
  }

  @Test
  fun advertisedCommands_respectsFeatureAvailability() {
    val commands = InvokeCommandRegistry.advertisedCommands(defaultFlags())

    assertContainsAll(commands, coreCommands)
    assertMissingAll(commands, optionalCommands + debugCommands)
  }

  @Test
  fun advertisedCommands_includesFeatureCommandsWhenEnabled() {
    val commands =
      InvokeCommandRegistry.advertisedCommands(
        defaultFlags(
          cameraEnabled = true,
          locationEnabled = true,
          smsAvailable = true,
          motionActivityAvailable = true,
          motionPedometerAvailable = true,
          debugBuild = true,
        ),
      )

    assertContainsAll(commands, coreCommands + optionalCommands + debugCommands)
  }

  @Test
  fun advertisedCommands_onlyIncludesSupportedMotionCommands() {
    val commands =
      InvokeCommandRegistry.advertisedCommands(
        NodeRuntimeFlags(
          cameraEnabled = false,
          locationEnabled = false,
          smsAvailable = false,
          voiceWakeEnabled = false,
          motionActivityAvailable = true,
          motionPedometerAvailable = false,
          debugBuild = false,
        ),
      )

    assertTrue(commands.contains(FluffBuzzMotionCommand.Activity.rawValue))
    assertFalse(commands.contains(FluffBuzzMotionCommand.Pedometer.rawValue))
  }

  private fun defaultFlags(
    cameraEnabled: Boolean = false,
    locationEnabled: Boolean = false,
    smsAvailable: Boolean = false,
    voiceWakeEnabled: Boolean = false,
    motionActivityAvailable: Boolean = false,
    motionPedometerAvailable: Boolean = false,
    debugBuild: Boolean = false,
  ): NodeRuntimeFlags =
    NodeRuntimeFlags(
      cameraEnabled = cameraEnabled,
      locationEnabled = locationEnabled,
      smsAvailable = smsAvailable,
      voiceWakeEnabled = voiceWakeEnabled,
      motionActivityAvailable = motionActivityAvailable,
      motionPedometerAvailable = motionPedometerAvailable,
      debugBuild = debugBuild,
    )

  private fun assertContainsAll(actual: List<String>, expected: Set<String>) {
    expected.forEach { value -> assertTrue(actual.contains(value)) }
  }

  private fun assertMissingAll(actual: List<String>, forbidden: Set<String>) {
    forbidden.forEach { value -> assertFalse(actual.contains(value)) }
  }
}
