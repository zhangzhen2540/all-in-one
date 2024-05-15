/*
 * Copyright 2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zz;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;
import lombok.Data;

/**
 * A class used to demonstrate how it's possible for PowerMock to mock private methods invocations.
 */
public class DataService {


    public boolean replaceData(final String dataId, final byte[] binaryData) {
        return modifyData(dataId, binaryData);
    }

    public boolean deleteData(final String dataId) {
        return modifyData(dataId, null);
    }

    /**
     * Modify the data.
     *
     * @param dataId     The ID of the data slot where the binary data will be stored.
     * @param binaryData The binary data that will be stored. If <code>null</code> this means that the data for the particular slot is
     *                   considered removed.
     * @return <code>true</code> if the operation was successful,
     * <code>false</code> otherwise.
     */
    private boolean modifyData(final String dataId, final byte[] binaryData) {
        /*
         * Imagine this method doing something complex and expensive.
         */
        return true;
    }


    public static void main(String[] args) {
        List<OreRecordInfo> oreRecordInfos = new ArrayList<>();
        ArrayList<OreRecordInfo> collect1 =
            oreRecordInfos.stream().collect(
                Collectors.collectingAndThen(
                    Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(OreRecordInfo::getOreName).thenComparing(OreRecordInfo::getPlantName))
                    ),
                    ArrayList::new
                )
            );
        BigDecimal planNum = collect1.stream().map(o -> {
            if (Objects.equals(o.getYukuoSendWeight(), "HENGXIAN")) {
                return BigDecimal.ZERO;
            }
            return new BigDecimal(o.getYukuoSendWeight());
        }).reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println(planNum);
    }

    @Data
    public class OreRecordInfo implements Serializable {

        private static final long serialVersionUID = 5931010462015545234L;


        private Long oreRecordId;


        private String oreName;


        private String region;

        private String businessRegion;


        private String dataCountDate;


        private String plantName;


        private String deptName;


        private String yesterdayFreight;


        private String dayFreight;


        private String freightIncrease;


        private String yukuoSendWeight;


        private String yukuoFinishRate;


        private String actualSendWeight;


        private String actualFinishRate;


        private String oreSendWeight;


        private String oreSendTruck;


        private String orePlanFinishRate;


        private String daySendWeight;

    }
}
