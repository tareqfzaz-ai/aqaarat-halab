import java.awt.BasicStroke
import java.awt.Color
import java.awt.Font
import java.awt.RenderingHints
import java.awt.font.TextLayout
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.tareqfzaz.aqaarat"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.tareqfzaz.aqaarat"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

val generateLauncherIcon = tasks.register("generateLauncherIcon") {
    doLast {
        val out = file("src/main/res/drawable/ic_launcher_text.png")
        out.parentFile.mkdirs()

        val image = BufferedImage(512, 512, BufferedImage.TYPE_INT_ARGB)
        val g = image.createGraphics()
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)

        val black = Color(17, 17, 17)
        val gold = Color(212, 175, 55)

        g.color = black
        g.fillRoundRect(0, 0, 512, 512, 70, 70)

        g.color = gold
        g.stroke = BasicStroke(10f)
        g.drawRoundRect(12, 12, 488, 488, 60, 60)

        val house = intArrayOf(95, 245, 256, 95, 417, 245, 417, 390, 95, 390)
        val roof = java.awt.Polygon(intArrayOf(95, 256, 417), intArrayOf(245, 95, 245), 3)
        g.fillPolygon(roof)
        g.fillRect(95, 245, 322, 145)

        g.color = black
        g.fillPolygon(java.awt.Polygon(intArrayOf(150, 256, 362), intArrayOf(255, 157, 255), 3))
        g.fillRect(150, 255, 212, 105)

        g.color = gold
        g.fillOval(230, 299, 52, 52)
        g.fillRect(250, 347, 12, 35)

        g.stroke = BasicStroke(18f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
        g.drawLine(330, 300, 435, 195)
        g.stroke = BasicStroke(16f)
        g.drawOval(410, 158, 64, 64)
        g.stroke = BasicStroke(14f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)
        g.drawLine(325, 305, 380, 360)
        g.drawLine(365, 265, 410, 310)

        val text = "عقارات الظاظا"
        g.font = Font("DejaVu Sans", Font.BOLD, 50)
        val layout = TextLayout(text, g.font, g.fontRenderContext)
        val bounds = layout.bounds
        val tx = (512f - bounds.width) / 2f - bounds.x
        layout.draw(g, tx, 468f)

        g.dispose()
        ImageIO.write(image, "png", out)
    }
}

tasks.named("preBuild").configure {
    dependsOn(generateLauncherIcon)
}

dependencies {
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.activity:activity-ktx:1.10.0")
}
