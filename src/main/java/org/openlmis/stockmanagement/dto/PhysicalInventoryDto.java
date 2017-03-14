/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2017 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Affero General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Affero General Public License for more details. You should have received a copy of
 * the GNU Affero General Public License along with this program. If not, see
 * http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.stockmanagement.dto;

import static java.util.stream.Collectors.toList;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class PhysicalInventoryDto {
  private UUID programId;

  private UUID facilityId;

  private Boolean isDraft;

  private ZonedDateTime occurredDate;

  private String signature;
  private String documentNumber;

  private List<PhysicalInventoryLineItemDto> lineItems;

  public List<StockEventDto> toEventDtos() {
    return lineItems.stream()
        .map(lineItem -> {
          StockEventDto stockEventDto = new StockEventDto();
          stockEventDto.setFacilityId(facilityId);
          stockEventDto.setProgramId(programId);
          stockEventDto.setOccurredDate(occurredDate);
          stockEventDto.setSignature(signature);
          stockEventDto.setDocumentNumber(documentNumber);

          stockEventDto.setOrderableId(lineItem.getOrderableDto().getId());
          stockEventDto.setQuantity(lineItem.getQuantity());
          return stockEventDto;
        })
        .collect(toList());
  }
}
