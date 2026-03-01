import Foundation

// Stable identifier used for both the macOS LaunchAgent label and Nix-managed defaults suite.
// nix-fluffbuzz writes app defaults into this suite to survive app bundle identifier churn.
let launchdLabel = "com.fluffbuzz.mac"
let gatewayLaunchdLabel = "com.fluffbuzz.gateway"
let onboardingVersionKey = "fluffbuzz.onboardingVersion"
let onboardingSeenKey = "fluffbuzz.onboardingSeen"
let currentOnboardingVersion = 7
let pauseDefaultsKey = "fluffbuzz.pauseEnabled"
let iconAnimationsEnabledKey = "fluffbuzz.iconAnimationsEnabled"
let swabbleEnabledKey = "fluffbuzz.swabbleEnabled"
let swabbleTriggersKey = "fluffbuzz.swabbleTriggers"
let voiceWakeTriggerChimeKey = "fluffbuzz.voiceWakeTriggerChime"
let voiceWakeSendChimeKey = "fluffbuzz.voiceWakeSendChime"
let showDockIconKey = "fluffbuzz.showDockIcon"
let defaultVoiceWakeTriggers = ["fluffbuzz"]
let voiceWakeMaxWords = 32
let voiceWakeMaxWordLength = 64
let voiceWakeMicKey = "fluffbuzz.voiceWakeMicID"
let voiceWakeMicNameKey = "fluffbuzz.voiceWakeMicName"
let voiceWakeLocaleKey = "fluffbuzz.voiceWakeLocaleID"
let voiceWakeAdditionalLocalesKey = "fluffbuzz.voiceWakeAdditionalLocaleIDs"
let voicePushToTalkEnabledKey = "fluffbuzz.voicePushToTalkEnabled"
let talkEnabledKey = "fluffbuzz.talkEnabled"
let iconOverrideKey = "fluffbuzz.iconOverride"
let connectionModeKey = "fluffbuzz.connectionMode"
let remoteTargetKey = "fluffbuzz.remoteTarget"
let remoteIdentityKey = "fluffbuzz.remoteIdentity"
let remoteProjectRootKey = "fluffbuzz.remoteProjectRoot"
let remoteCliPathKey = "fluffbuzz.remoteCliPath"
let canvasEnabledKey = "fluffbuzz.canvasEnabled"
let cameraEnabledKey = "fluffbuzz.cameraEnabled"
let systemRunPolicyKey = "fluffbuzz.systemRunPolicy"
let systemRunAllowlistKey = "fluffbuzz.systemRunAllowlist"
let systemRunEnabledKey = "fluffbuzz.systemRunEnabled"
let locationModeKey = "fluffbuzz.locationMode"
let locationPreciseKey = "fluffbuzz.locationPreciseEnabled"
let peekabooBridgeEnabledKey = "fluffbuzz.peekabooBridgeEnabled"
let deepLinkKeyKey = "fluffbuzz.deepLinkKey"
let modelCatalogPathKey = "fluffbuzz.modelCatalogPath"
let modelCatalogReloadKey = "fluffbuzz.modelCatalogReload"
let cliInstallPromptedVersionKey = "fluffbuzz.cliInstallPromptedVersion"
let heartbeatsEnabledKey = "fluffbuzz.heartbeatsEnabled"
let debugPaneEnabledKey = "fluffbuzz.debugPaneEnabled"
let debugFileLogEnabledKey = "fluffbuzz.debug.fileLogEnabled"
let appLogLevelKey = "fluffbuzz.debug.appLogLevel"
let voiceWakeSupported: Bool = ProcessInfo.processInfo.operatingSystemVersion.majorVersion >= 26
