package com.nguylinc.streamsapi3

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.util.UUID
import jdk.jfr.Recording
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class StreamsApi3Application

fun main(args: Array<String>) {
    runApplication<StreamsApi3Application>(*args)
}

@Entity
class StreamRecording(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,
    val url: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stream_id")
    val stream: Stream? = null,
)

@Entity
class StreamSchedule(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,
    val url: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stream_id")
    val stream: Stream? = null,
)

@Entity
class Stream(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,
    val name: String,
)

interface StreamRecordingRepository : JpaRepository<StreamRecording, UUID> {
    fun findByStreamId(streamId: UUID): List<StreamRecording>
}

interface ScheduleRepository : JpaRepository<StreamSchedule, UUID>

interface StreamRepository : JpaRepository<Stream, UUID>

@Service
class RecordingService(@Autowired private val streamRecordingRepository: StreamRecordingRepository) {
    fun getRecordingsByStream(streamId: UUID): List<StreamRecording> {
        return streamRecordingRepository.findByStreamId(streamId)
    }
}

@RestController
@RequestMapping("/api/recordings")
class RecordingController(@Autowired private val recordingService: RecordingService) {

    @GetMapping("/stream/{streamId}")
    fun getRecordingsByStream(@PathVariable streamId: UUID): List<StreamRecording> {
        return recordingService.getRecordingsByStream(streamId)
    }
}
