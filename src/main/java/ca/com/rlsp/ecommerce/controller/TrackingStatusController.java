package ca.com.rlsp.ecommerce.controller;

import ca.com.rlsp.ecommerce.model.TrackingStatus;
import ca.com.rlsp.ecommerce.model.dto.TrackingStatusDTO;
import ca.com.rlsp.ecommerce.service.TrackingStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TrackingStatusController {

    private TrackingStatusService trackingStatusService;

    public TrackingStatusController(TrackingStatusService trackingStatusService) {
        this.trackingStatusService = trackingStatusService;
    }

    @ResponseBody
    @GetMapping(value = "/listTrackingStatus/{saleId}")
    public ResponseEntity<List<TrackingStatusDTO>> listTrackingStatus (@PathVariable("saleId") Long salesId){

        List<TrackingStatus> listTrackingStatus =	 trackingStatusService.getAllTrackingStatus(salesId);

        List<TrackingStatusDTO> listTrackingStatusDTO = new ArrayList<>();

        for(TrackingStatus lts : listTrackingStatus){
            TrackingStatusDTO dto = new TrackingStatusDTO();
            dto.setCountry(lts.getCountry());
            dto.setCity(lts.getCity());
            dto.setStatus(lts.getStatus());
            dto.setProvince(lts.getProvince());
            dto.setDistributionHub(lts.getDistributionHub());

            listTrackingStatusDTO.add(dto);
        }


        return new ResponseEntity<List<TrackingStatusDTO>>(listTrackingStatusDTO, HttpStatus.OK);

    }

}
