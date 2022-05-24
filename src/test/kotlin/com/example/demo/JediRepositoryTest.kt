package com.example.demo

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach

@DataJpaTest
class JediRepositoryTest(@Autowired private val jediRepository: JediRepository) : Assertions() {
  var saved: List<Jedi> = emptyList()

  @BeforeEach
  fun `insert testdata`(){
    saved = jediRepository.saveAll(listOf(
        Jedi(1, "General Kenobi", "A bold one"),
        Jedi(2, "Anakin Skywalker", "Not a Jedi Master"),
        Jedi(3, "Mace Windu", "Purple Lightsaber"),
    ))
  }

  @Test
  fun `searchContains - matches within`() {
    val result = jediRepository.searchContains(saved[0].name, "old")
    assertThat(result.size).isEqualTo(1)
    assertThat(result.first().id).isEqualTo(1)
  }

  @Test
  fun `searchStartsWith - matches start`() {
    val result = jediRepository.searchStartsWith(saved[0].name, "A bold")
    assertThat(result.size).isEqualTo(1)
    assertThat(result.first().id).isEqualTo(1)
  }
  @Test
  fun `searchEndsWith - matches end`() {
    val result = jediRepository.searchEndsWith(saved[0].name, "one")
    assertThat(result.size).isEqualTo(1)
    assertThat(result.first().id).isEqualTo(1)
  }

  @Test
  fun `searchContains - handles optional (null) name`() {
    var result = jediRepository.searchContains(null, "er")
    assertThat(result.size).isEqualTo(2)
    assertThat(result).contains(saved[1])
    assertThat(result).contains(saved[2])
  }

  @Test
  fun `searchContains - handles optional (null) desc`() {
    var result = jediRepository.searchContains("Mace Windu", null)
    assertThat(result.size).isEqualTo(1)
    assertThat(result.first().id).isEqualTo(3)
  }

  @Test
  fun `searchContains - handles optional (null) both`() {
    var result = jediRepository.searchContains(null, null)
    assertThat(result.size).isEqualTo(3)
  }

  @Test
  fun `searchStartsWith - handles optional (null) desc`() {
    var result = jediRepository.searchStartsWith("Mace Windu", null)
    assertThat(result.size).isEqualTo(1)
    assertThat(result.first().id).isEqualTo(3)
  }

  @Test
  fun `searchEndsWith - handles optional (null) desc`() {
    var result = jediRepository.searchEndsWith("Mace Windu", null)
    assertThat(result.size).isEqualTo(1)
    assertThat(result.first().id).isEqualTo(3)
  }
}