package com.itime.sernachat.domain

interface EntityMapper<D, E> {
    fun toEntity(dto: D): E
    fun toDto(entity: E): D
    fun toEntity(dtoList: MutableList<D>):MutableList<E>
    fun toDto(dtoList: MutableList<E>):MutableList<D>
}