package com.example.demo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface JediRepository : JpaRepository<Jedi, Long> {

  @Query("""
    select j from Jedi j
    where (:name is null or j.name = :name)
    and (:desc is null or j.description like %:desc%)
  """)
  fun searchContains(
      @Param("name") name: String?,
      @Param("desc") desc: String?,
  ): List<Jedi>

  @Query("""
    select j from Jedi j
    where (:name is null or j.name = :name)
    and (:desc is null or j.description like :desc%)
  """)
  fun searchStartsWith(
      @Param("name") name: String?,
      @Param("desc") desc: String?,
  ): List<Jedi>

  @Query("""
    select j from Jedi j
    where (:name is null or j.name = :name)
    and (:desc is null or j.description like %:desc)
  """)
  fun searchEndsWith(
      @Param("name") name: String?,
      @Param("desc") desc: String?,
  ): List<Jedi>
}