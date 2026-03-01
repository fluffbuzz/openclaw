// swift-tools-version: 6.2

import PackageDescription

let package = Package(
    name: "FluffBuzzKit",
    platforms: [
        .iOS(.v18),
        .macOS(.v15),
    ],
    products: [
        .library(name: "FluffBuzzProtocol", targets: ["FluffBuzzProtocol"]),
        .library(name: "FluffBuzzKit", targets: ["FluffBuzzKit"]),
        .library(name: "FluffBuzzChatUI", targets: ["FluffBuzzChatUI"]),
    ],
    dependencies: [
        .package(url: "https://github.com/steipete/ElevenLabsKit", exact: "0.1.0"),
        .package(url: "https://github.com/gonzalezreal/textual", exact: "0.3.1"),
    ],
    targets: [
        .target(
            name: "FluffBuzzProtocol",
            path: "Sources/FluffBuzzProtocol",
            swiftSettings: [
                .enableUpcomingFeature("StrictConcurrency"),
            ]),
        .target(
            name: "FluffBuzzKit",
            dependencies: [
                "FluffBuzzProtocol",
                .product(name: "ElevenLabsKit", package: "ElevenLabsKit"),
            ],
            path: "Sources/FluffBuzzKit",
            resources: [
                .process("Resources"),
            ],
            swiftSettings: [
                .enableUpcomingFeature("StrictConcurrency"),
            ]),
        .target(
            name: "FluffBuzzChatUI",
            dependencies: [
                "FluffBuzzKit",
                .product(
                    name: "Textual",
                    package: "textual",
                    condition: .when(platforms: [.macOS, .iOS])),
            ],
            path: "Sources/FluffBuzzChatUI",
            swiftSettings: [
                .enableUpcomingFeature("StrictConcurrency"),
            ]),
        .testTarget(
            name: "FluffBuzzKitTests",
            dependencies: ["FluffBuzzKit", "FluffBuzzChatUI"],
            path: "Tests/FluffBuzzKitTests",
            swiftSettings: [
                .enableUpcomingFeature("StrictConcurrency"),
                .enableExperimentalFeature("SwiftTesting"),
            ]),
    ])
