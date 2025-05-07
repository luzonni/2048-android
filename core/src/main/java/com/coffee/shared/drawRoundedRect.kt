package com.coffee.shared

import com.badlogic.gdx.graphics.glutils.ShapeRenderer

fun drawRoundedRect(
    shapeRenderer: ShapeRenderer,
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    radius: Float
) {
    shapeRenderer.rect(x + radius, y + radius, width - 2 * radius, height - 2 * radius)
    shapeRenderer.rect(x + radius, y, width - 2 * radius, radius) // baixo
    shapeRenderer.rect(x + radius, y + height - radius, width - 2 * radius, radius) // cima
    shapeRenderer.rect(x, y + radius, radius, height - 2 * radius) // esquerda
    shapeRenderer.rect(x + width - radius, y + radius, radius, height - 2 * radius) // direita
    shapeRenderer.circle(x + radius, y + radius, radius) // inferior esquerdo
    shapeRenderer.circle(x + width - radius, y + radius, radius) // inferior direito
    shapeRenderer.circle(x + radius, y + height - radius, radius) // superior esquerdo
    shapeRenderer.circle(x + width - radius, y + height - radius, radius) // superior direito
}
