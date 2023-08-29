import SwiftUI
import shared

@main
struct iOSApp: App {
    @Environment(\.scenePhase) private var scenePhase
    private var lifecycleHolder: LifecycleHolder { LifecycleHolder() }
    
    var body: some Scene {
        WindowGroup {
            GeometryReader { geo in
                ComposeView(
                    lifecycle: lifecycleHolder.lifecycle,
                    topSafeArea: Float(geo.safeAreaInsets.top),
                    bottomSafeArea: Float(geo.safeAreaInsets.bottom)
                )
                .ignoresSafeArea()
                .onTapGesture {
                    // Hide keyboard on tap outside of TextField
                    UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
                }
            }
        }
    }
}

