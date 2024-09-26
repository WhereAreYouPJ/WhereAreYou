package com.onmyway.ui.component

import androidx.annotation.Keep
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Glow(
    val radius: Dp = 8.dp, // Controls glow size
    val xShifting: Dp = 0.dp, // Adjusts horizontal position
    val yShifting: Dp = 0.dp, // Adjusts vertical position
)

//fun createPath(width: Float, height: Float): Path {
//    return Path().apply {
//        // Move to Center(c)
//        moveTo((width / 2), (height / 2))
//        // Draw the right side
//        cubicTo(
//            x1 = width, y1 = 0f, // 1
//            x2 = width, y2 = height, // 2
//            x3 = (width / 2), (height / 2) // Center(c)
//        )
//        // Draw the left side
//        cubicTo(
//            x1 = 0f, y1 = 0f, // 3
//            x2 = 0f, y2 = height, // 4
//            x3 = (width / 2), (height / 2) // Center(c)
//        )
//    }
//}

fun createPath(width: Float, height: Float): Path {
    return Path().apply {
        this.addOval(
            Rect(
                left = width * 0.1f,
                top = height * 0.1f,
                right = width * 0.9f,
                bottom = height * 0.9f
            )
        )
    }
}

fun calculatePathSegment(path: Path, pathCompletion: Float): Path {
    // Create a PathMeasure instance for the given path
    val pathMeasure = PathMeasure().apply {
        setPath(path, false)
    }

    // Create a new Path to store the segment
    val pathSegment = Path()

    // Calculate the distance to stop drawing the path segment
    val stopDistance = when {
        (pathCompletion < 1f) -> (pathCompletion * pathMeasure.length)
        else -> pathMeasure.length
    }

    // Calculate the distance to start drawing the path segment
    val startDistance = when {
        (pathCompletion > 1f) -> ((pathCompletion - 1f) * pathMeasure.length)
        else -> 0f
    }

    // Retrieve the segment of the path based on start and stop distances
    pathMeasure.getSegment(startDistance, stopDistance, pathSegment, true)
    return pathSegment
}

// Extend the DrawScope to utilize 'toPx()' and access the Canvas size
fun DrawScope.setupPaint(
    strokeWidth: Dp,
    strokeCap: StrokeCap,
    brush: Brush,
): Paint {
    return Paint().apply paint@{
        // Set anti-aliasing for smoother edges
        this@paint.isAntiAlias = true
        // Set the painting style to Stroke (outline)
        this@paint.style = PaintingStyle.Stroke
        // Set the stroke width by converting from Dp to pixels
        this@paint.strokeWidth = strokeWidth.toPx()
        // Set the stroke cap style
        this@paint.strokeCap = strokeCap

        // Apply the brush to the paint
        brush.applyTo(size, this@paint, 1f)
    }
}

fun Glow.applyToPaint(paint: Paint, density: Density) = with(density) {
    val frameworkPaint = paint.asFrameworkPaint()
    frameworkPaint.setShadowLayer(
        /* radius = */ radius.toPx(),
        /* dx = */ xShifting.toPx(),
        /* dy = */ yShifting.toPx(),
        /* shadowColor = */ android.graphics.Color.WHITE
    )
}

fun DrawScope.drawPathSegment(pathSegment: Path, paint: Paint) {
    drawIntoCanvas { canvas ->
        canvas.drawPath(pathSegment, paint)
    }
}

fun DrawScope.drawPathPlaceholder(
    path: Path,
    strokeWidth: Dp,
    strokeCap: StrokeCap,
    placeholderColor: Color
) {
    drawPath(
        path = path,
        color = placeholderColor,
        style = Stroke(
            width = strokeWidth.toPx(),
            cap = strokeCap
        )
    )
}

@Composable
fun InfinityLoader(
    modifier: Modifier,
    brush: Brush,
    duration: Int = 2800,
    strokeWidth: Dp = 4.dp,
    strokeCap: StrokeCap = StrokeCap.Round,
    glow: Glow? = null,
    placeholderColor: Color? = null
) {
    // Set up infinite animation
    val infiniteTransition = rememberInfiniteTransition("PathTransition")

    // Animate path completion
    val pathCompletion by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(duration, easing = FastOutLinearInEasing)
        ),
        label = "PathCompletion"
    )

    val infTransition = rememberInfiniteTransition("")
    val circleTransition by infTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(700, easing = LinearEasing)
        ),
        label = ""
    )

    Canvas(modifier.rotate(circleTransition)) {
        // Create path and calculate segment
        val path = createPath(size.width, size.height)
        val pathSegment = calculatePathSegment(path, pathCompletion)

        // Set up paint for drawing
        val paint = setupPaint(strokeWidth, strokeCap, brush)

        // Apply glow effect, if provided
        glow?.applyToPaint(paint, this)

        // Draw placeholder, if color is provided
        placeholderColor?.let { color ->
            drawPathPlaceholder(path, strokeWidth, strokeCap, color)
        }

        // Draw the path segment
        drawPathSegment(pathSegment, paint)
    }
}

@Keep
@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun InfinityLoaderPreview() {
    InfinityLoader(
        brush = Brush.horizontalGradient(
            colors = listOf(Color(0xFFFF0000), Color(0xFF0000FF))
        ),
        modifier = Modifier
            .width(100.dp)
            .height(100.dp),
        glow = Glow()
    )
}

@Keep
@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun WhiteInfinityLoaderPreview() {
    InfinityLoader(
        brush = SolidColor(Color(0xFFFFFFFF)),
        modifier = Modifier
            .width(100.dp)
            .height(100.dp),
        glow = Glow()
    )
}