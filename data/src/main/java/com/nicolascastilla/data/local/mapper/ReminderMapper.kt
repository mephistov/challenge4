package com.nicolascastilla.data.local.mapper

import com.nicolascastilla.data.local.entities.ReminderModel
import com.nicolascastilla.domain.entities.ReminderEntity

internal fun ReminderModel.toEntity() = ReminderEntity(
    id = id,
    name = name,
    description = description,
    type = type,
    date = date,
    image = image,
    humaDate = huma_date,
    status = status
)

internal fun List<ReminderModel>.toEntities() = map{it.toEntity()}

internal fun ReminderEntity.toModel() = ReminderModel(
    id = id,
    name = name,
    description = description,
    type = type,
    date = date,
    image = image,
    huma_date = humaDate,
    status = status
)

internal fun List<ReminderEntity>.toModels() = map{it.toModel()}