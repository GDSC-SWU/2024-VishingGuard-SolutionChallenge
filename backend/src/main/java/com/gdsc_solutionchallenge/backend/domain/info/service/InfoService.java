package com.gdsc_solutionchallenge.backend.domain.info.service;

import com.gdsc_solutionchallenge.backend.domain.info.domain.InfoRepository;
import com.gdsc_solutionchallenge.backend.domain.info.domain.Prevention;
import com.gdsc_solutionchallenge.backend.domain.info.domain.ReportPlace;
import com.gdsc_solutionchallenge.backend.domain.info.domain.ReportProcedure;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.cloud.StorageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InfoService {
    private final InfoRepository infoRepository;

    /**
     * Load information about the report procedure.
     * @return List of ReportProcedure objects containing report procedure details.
     * @throws Exception if an error occurs while fetching report procedure information.
     */
    public List<ReportProcedure> loadReportProcedure() throws Exception {
        List<ReportProcedure> reportProcedures = infoRepository.getAllReportProcedure();

        return reportProcedures;
    }

    /**
     * Load information about prevention measures.
     * @return List of Prevention objects containing details about prevention measures.
     * @throws Exception if an error occurs while fetching prevention information.
     */
    public List<Prevention> loadPrevention() throws Exception {
        List<Prevention> preventions = infoRepository.getAllPrevention();

        return preventions;
    }

    /**
     * Load information about places to report.
     * @return List of ReportPlace objects containing details about places to report.
     * @throws Exception if an error occurs while fetching report place information.
     */
    public List<ReportPlace> loadReportPlace() throws Exception {
        List<ReportPlace> reportPlaces = infoRepository.getAllReportPlace();

        return reportPlaces;
    }
}
