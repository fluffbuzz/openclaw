// swift-tools-version: 6.2
// Package manifest for the FluffBuzz macOS companion (menu bar app + IPC library).

import PackageDescription

let package = Package(
    name: "FluffBuzz",
    platforms: [
        .macOS(.v15),
    ],
    products: [
        .library(name: "FluffBuzzIPC", targets: ["FluffBuzzIPC"]),
        .library(name: "FluffBuzzDiscovery", targets: ["FluffBuzzDiscovery"]),
        .executable(name: "FluffBuzz", targets: ["FluffBuzz"]),
        .executable(name: "fluffbuzz-mac", targets: ["FluffBuzzMacCLI"]),
    ],
    dependencies: [
        .package(url: "https://github.com/orchetect/MenuBarExtraAccess", exact: "1.2.2"),
        .package(url: "https://github.com/swiftlang/swift-subprocess.git", from: "0.1.0"),
        .package(url: "https://github.com/apple/swift-log.git", from: "1.8.0"),
        .package(url: "https://github.com/sparkle-project/Sparkle", from: "2.8.1"),
        .package(url: "https://github.com/steipete/Peekaboo.git", branch: "main"),
        .package(path: "../shared/FluffBuzzKit"),
        .package(path: "../../Swabble"),
    ],
    targets: [
        .target(
            name: "FluffBuzzIPC",
            dependencies: [],
            swiftSettings: [
                .enableUpcomingFeature("StrictConcurrency"),
            ]),
        .target(
            name: "FluffBuzzDiscovery",
            dependencies: [
                .product(name: "FluffBuzzKit", package: "FluffBuzzKit"),
            ],
            path: "Sources/FluffBuzzDiscovery",
            swiftSettings: [
                .enableUpcomingFeature("StrictConcurrency"),
            ]),
        .executableTarget(
            name: "FluffBuzz",
            dependencies: [
                "FluffBuzzIPC",
                "FluffBuzzDiscovery",
                .product(name: "FluffBuzzKit", package: "FluffBuzzKit"),
                .product(name: "FluffBuzzChatUI", package: "FluffBuzzKit"),
                .product(name: "FluffBuzzProtocol", package: "FluffBuzzKit"),
                .product(name: "SwabbleKit", package: "swabble"),
                .product(name: "MenuBarExtraAccess", package: "MenuBarExtraAccess"),
                .product(name: "Subprocess", package: "swift-subprocess"),
                .product(name: "Logging", package: "swift-log"),
                .product(name: "Sparkle", package: "Sparkle"),
                .product(name: "PeekabooBridge", package: "Peekaboo"),
                .product(name: "PeekabooAutomationKit", package: "Peekaboo"),
            ],
            exclude: [
                "Resources/Info.plist",
            ],
            resources: [
                .copy("Resources/FluffBuzz.icns"),
                .copy("Resources/DeviceModels"),
            ],
            swiftSettings: [
                .enableUpcomingFeature("StrictConcurrency"),
            ]),
        .executableTarget(
            name: "FluffBuzzMacCLI",
            dependencies: [
                "FluffBuzzDiscovery",
                .product(name: "FluffBuzzKit", package: "FluffBuzzKit"),
                .product(name: "FluffBuzzProtocol", package: "FluffBuzzKit"),
            ],
            path: "Sources/FluffBuzzMacCLI",
            swiftSettings: [
                .enableUpcomingFeature("StrictConcurrency"),
            ]),
        .testTarget(
            name: "FluffBuzzIPCTests",
            dependencies: [
                "FluffBuzzIPC",
                "FluffBuzz",
                "FluffBuzzDiscovery",
                .product(name: "FluffBuzzProtocol", package: "FluffBuzzKit"),
                .product(name: "SwabbleKit", package: "swabble"),
            ],
            swiftSettings: [
                .enableUpcomingFeature("StrictConcurrency"),
                .enableExperimentalFeature("SwiftTesting"),
            ]),
    ])
