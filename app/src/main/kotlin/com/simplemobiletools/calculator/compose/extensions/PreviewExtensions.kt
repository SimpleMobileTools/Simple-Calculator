package com.simplemobiletools.calculator.compose.extensions

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

const val LIGHT = "Light"
const val DARK = "Dark"

@MyDevicesDarkOnly
@MyDevicesLightOnly
annotation class MyDevices

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, device = Devices.PIXEL_4_XL, name = "6.3 inches dark", group = DARK)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, device = Devices.PIXEL_2, name = "5.0 inches dark", group = DARK)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, device = Devices.PIXEL_2_XL, name = "6.0 inches dark", group = DARK)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, device = Devices.PIXEL_4_XL, name = "5.5 inches dark", group = DARK)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, device = Devices.PIXEL_4, name = "5.7 inches dark", group = DARK)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, device = Devices.TABLET, name = "Tablet", group = DARK)
annotation class MyDevicesDarkOnly

@Preview(showBackground = true, device = Devices.PIXEL_4_XL, name = "6.3 inches light", group = LIGHT)
@Preview(showBackground = true, device = Devices.PIXEL_2, name = "5.0 inches light", group = LIGHT)
@Preview(showBackground = true, device = Devices.PIXEL_2_XL, name = "6.0 inches light", group = LIGHT)
@Preview(showBackground = true, device = Devices.PIXEL_XL, name = "5.5 inches light", group = LIGHT)
@Preview(showBackground = true, device = Devices.PIXEL_4, name = "5.7 inches light", group = LIGHT)
@Preview(showBackground = true, device = Devices.TABLET, name = "Tablet", group = DARK)
annotation class MyDevicesLightOnly
