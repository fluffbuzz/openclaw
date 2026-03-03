package com.fluffbuzz.android.node

import com.fluffbuzz.android.protocol.FluffBuzzCalendarCommand
import com.fluffbuzz.android.protocol.FluffBuzzCanvasA2UICommand
import com.fluffbuzz.android.protocol.FluffBuzzCanvasCommand
import com.fluffbuzz.android.protocol.FluffBuzzCameraCommand
import com.fluffbuzz.android.protocol.FluffBuzzCapability
import com.fluffbuzz.android.protocol.FluffBuzzContactsCommand
import com.fluffbuzz.android.protocol.FluffBuzzDeviceCommand
import com.fluffbuzz.android.protocol.FluffBuzzLocationCommand
import com.fluffbuzz.android.protocol.FluffBuzzMotionCommand
import com.fluffbuzz.android.protocol.FluffBuzzNotificationsCommand
import com.fluffbuzz.android.protocol.FluffBuzzPhotosCommand
import com.fluffbuzz.android.protocol.FluffBuzzScreenCommand
import com.fluffbuzz.android.protocol.FluffBuzzSmsCommand
import com.fluffbuzz.android.protocol.FluffBuzzSystemCommand

data class NodeRuntimeFlags(
  val cameraEnabled: Boolean,
  val locationEnabled: Boolean,
  val smsAvailable: Boolean,
  val voiceWakeEnabled: Boolean,
  val motionActivityAvailable: Boolean,
  val motionPedometerAvailable: Boolean,
  val debugBuild: Boolean,
)

enum class InvokeCommandAvailability {
  Always,
  CameraEnabled,
  LocationEnabled,
  SmsAvailable,
  MotionActivityAvailable,
  MotionPedometerAvailable,
  DebugBuild,
}

enum class NodeCapabilityAvailability {
  Always,
  CameraEnabled,
  LocationEnabled,
  SmsAvailable,
  VoiceWakeEnabled,
  MotionAvailable,
}

data class NodeCapabilitySpec(
  val name: String,
  val availability: NodeCapabilityAvailability = NodeCapabilityAvailability.Always,
)

data class InvokeCommandSpec(
  val name: String,
  val requiresForeground: Boolean = false,
  val availability: InvokeCommandAvailability = InvokeCommandAvailability.Always,
)

object InvokeCommandRegistry {
  val capabilityManifest: List<NodeCapabilitySpec> =
    listOf(
      NodeCapabilitySpec(name = FluffBuzzCapability.Canvas.rawValue),
      NodeCapabilitySpec(name = FluffBuzzCapability.Screen.rawValue),
      NodeCapabilitySpec(name = FluffBuzzCapability.Device.rawValue),
      NodeCapabilitySpec(name = FluffBuzzCapability.Notifications.rawValue),
      NodeCapabilitySpec(name = FluffBuzzCapability.System.rawValue),
      NodeCapabilitySpec(name = FluffBuzzCapability.AppUpdate.rawValue),
      NodeCapabilitySpec(
        name = FluffBuzzCapability.Camera.rawValue,
        availability = NodeCapabilityAvailability.CameraEnabled,
      ),
      NodeCapabilitySpec(
        name = FluffBuzzCapability.Sms.rawValue,
        availability = NodeCapabilityAvailability.SmsAvailable,
      ),
      NodeCapabilitySpec(
        name = FluffBuzzCapability.VoiceWake.rawValue,
        availability = NodeCapabilityAvailability.VoiceWakeEnabled,
      ),
      NodeCapabilitySpec(
        name = FluffBuzzCapability.Location.rawValue,
        availability = NodeCapabilityAvailability.LocationEnabled,
      ),
      NodeCapabilitySpec(name = FluffBuzzCapability.Photos.rawValue),
      NodeCapabilitySpec(name = FluffBuzzCapability.Contacts.rawValue),
      NodeCapabilitySpec(name = FluffBuzzCapability.Calendar.rawValue),
      NodeCapabilitySpec(
        name = FluffBuzzCapability.Motion.rawValue,
        availability = NodeCapabilityAvailability.MotionAvailable,
      ),
    )

  val all: List<InvokeCommandSpec> =
    listOf(
      InvokeCommandSpec(
        name = FluffBuzzCanvasCommand.Present.rawValue,
        requiresForeground = true,
      ),
      InvokeCommandSpec(
        name = FluffBuzzCanvasCommand.Hide.rawValue,
        requiresForeground = true,
      ),
      InvokeCommandSpec(
        name = FluffBuzzCanvasCommand.Navigate.rawValue,
        requiresForeground = true,
      ),
      InvokeCommandSpec(
        name = FluffBuzzCanvasCommand.Eval.rawValue,
        requiresForeground = true,
      ),
      InvokeCommandSpec(
        name = FluffBuzzCanvasCommand.Snapshot.rawValue,
        requiresForeground = true,
      ),
      InvokeCommandSpec(
        name = FluffBuzzCanvasA2UICommand.Push.rawValue,
        requiresForeground = true,
      ),
      InvokeCommandSpec(
        name = FluffBuzzCanvasA2UICommand.PushJSONL.rawValue,
        requiresForeground = true,
      ),
      InvokeCommandSpec(
        name = FluffBuzzCanvasA2UICommand.Reset.rawValue,
        requiresForeground = true,
      ),
      InvokeCommandSpec(
        name = FluffBuzzScreenCommand.Record.rawValue,
        requiresForeground = true,
      ),
      InvokeCommandSpec(
        name = FluffBuzzSystemCommand.Notify.rawValue,
      ),
      InvokeCommandSpec(
        name = FluffBuzzCameraCommand.List.rawValue,
        requiresForeground = true,
        availability = InvokeCommandAvailability.CameraEnabled,
      ),
      InvokeCommandSpec(
        name = FluffBuzzCameraCommand.Snap.rawValue,
        requiresForeground = true,
        availability = InvokeCommandAvailability.CameraEnabled,
      ),
      InvokeCommandSpec(
        name = FluffBuzzCameraCommand.Clip.rawValue,
        requiresForeground = true,
        availability = InvokeCommandAvailability.CameraEnabled,
      ),
      InvokeCommandSpec(
        name = FluffBuzzLocationCommand.Get.rawValue,
        availability = InvokeCommandAvailability.LocationEnabled,
      ),
      InvokeCommandSpec(
        name = FluffBuzzDeviceCommand.Status.rawValue,
      ),
      InvokeCommandSpec(
        name = FluffBuzzDeviceCommand.Info.rawValue,
      ),
      InvokeCommandSpec(
        name = FluffBuzzDeviceCommand.Permissions.rawValue,
      ),
      InvokeCommandSpec(
        name = FluffBuzzDeviceCommand.Health.rawValue,
      ),
      InvokeCommandSpec(
        name = FluffBuzzNotificationsCommand.List.rawValue,
      ),
      InvokeCommandSpec(
        name = FluffBuzzNotificationsCommand.Actions.rawValue,
      ),
      InvokeCommandSpec(
        name = FluffBuzzPhotosCommand.Latest.rawValue,
      ),
      InvokeCommandSpec(
        name = FluffBuzzContactsCommand.Search.rawValue,
      ),
      InvokeCommandSpec(
        name = FluffBuzzContactsCommand.Add.rawValue,
      ),
      InvokeCommandSpec(
        name = FluffBuzzCalendarCommand.Events.rawValue,
      ),
      InvokeCommandSpec(
        name = FluffBuzzCalendarCommand.Add.rawValue,
      ),
      InvokeCommandSpec(
        name = FluffBuzzMotionCommand.Activity.rawValue,
        availability = InvokeCommandAvailability.MotionActivityAvailable,
      ),
      InvokeCommandSpec(
        name = FluffBuzzMotionCommand.Pedometer.rawValue,
        availability = InvokeCommandAvailability.MotionPedometerAvailable,
      ),
      InvokeCommandSpec(
        name = FluffBuzzSmsCommand.Send.rawValue,
        availability = InvokeCommandAvailability.SmsAvailable,
      ),
      InvokeCommandSpec(
        name = "debug.logs",
        availability = InvokeCommandAvailability.DebugBuild,
      ),
      InvokeCommandSpec(
        name = "debug.ed25519",
        availability = InvokeCommandAvailability.DebugBuild,
      ),
      InvokeCommandSpec(name = "app.update"),
    )

  private val byNameInternal: Map<String, InvokeCommandSpec> = all.associateBy { it.name }

  fun find(command: String): InvokeCommandSpec? = byNameInternal[command]

  fun advertisedCapabilities(flags: NodeRuntimeFlags): List<String> {
    return capabilityManifest
      .filter { spec ->
        when (spec.availability) {
          NodeCapabilityAvailability.Always -> true
          NodeCapabilityAvailability.CameraEnabled -> flags.cameraEnabled
          NodeCapabilityAvailability.LocationEnabled -> flags.locationEnabled
          NodeCapabilityAvailability.SmsAvailable -> flags.smsAvailable
          NodeCapabilityAvailability.VoiceWakeEnabled -> flags.voiceWakeEnabled
          NodeCapabilityAvailability.MotionAvailable -> flags.motionActivityAvailable || flags.motionPedometerAvailable
        }
      }
      .map { it.name }
  }

  fun advertisedCommands(flags: NodeRuntimeFlags): List<String> {
    return all
      .filter { spec ->
        when (spec.availability) {
          InvokeCommandAvailability.Always -> true
          InvokeCommandAvailability.CameraEnabled -> flags.cameraEnabled
          InvokeCommandAvailability.LocationEnabled -> flags.locationEnabled
          InvokeCommandAvailability.SmsAvailable -> flags.smsAvailable
          InvokeCommandAvailability.MotionActivityAvailable -> flags.motionActivityAvailable
          InvokeCommandAvailability.MotionPedometerAvailable -> flags.motionPedometerAvailable
          InvokeCommandAvailability.DebugBuild -> flags.debugBuild
        }
      }
      .map { it.name }
  }
}
