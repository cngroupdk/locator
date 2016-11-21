package dk.cngroup.intranet.locator;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dk.cngroup.intranet.locator.buildingcomponents.Floor;
import dk.cngroup.intranet.locator.buildingcomponents.Room;
import dk.cngroup.intranet.locator.repositories.FloorsRepository;
import dk.cngroup.intranet.locator.repositories.RoomsRepository;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

/**
 * Created by cano on 20.10.2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HttpRequestsTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FloorsRepository floorRepository;

    @Autowired
    private RoomsRepository roomRepository;

    @Test
    public void employeesRequestReturnsJSONData() throws Exception {

        this.mockMvc.perform(get("/employees")).andDo(print()).andExpect(status().isOk())
            .andExpect(content().json(
                "[" +
                    "{" +
                        "\"employeeGuid\":6," +
                        "\"managerId\":\"dobias\"," +
                        "\"firstName\":\"Victor Isaias\"," +
                        "\"lastName\":\"Cano Becerril\"," +
                        "\"abbreviation\":\"VCB\"," +
                        "\"role\":\"Developer\"," +
                        "\"email\":\"cano@cngroup.cz\"," +
                        "\"location\":\"K1@2nd Floor@KR9\"," +
                        "\"id\":\"cano\"" +
                    "}," +
                    "{" +
                        "\"employeeGuid\":4," +
                        "\"managerId\":\"dobias\"," +
                        "\"firstName\":\"Tomas\"," +
                        "\"lastName\":\"Kolaci\"," +
                        "\"abbreviation\":\"TKO\"," +
                        "\"role\":\"Developer\"," +
                        "\"email\":\"kolaci@cngroup.cz\"," +
                        "\"location\":\"E13@Ground@20\"," +
                        "\"id\":\"kolaci\"" +
                    "}," +
                    "{" +
                        "\"employeeGuid\":1," +
                        "\"managerId\":\"dobias\"," +
                        "\"firstName\":\"Vit\"," +
                        "\"lastName\":\"Koma\"," +
                        "\"abbreviation\":\"VKO\"," +
                        "\"role\":\"Developer\"," +
                        "\"email\":\"koma@cngroup.cz\"," +
                        "\"location\":\"K1@2nd Floor@KR9\"," +
                        "\"id\":\"koma\"" +
                    "}," +
                    "{" +
                        "\"employeeGuid\":5," +
                        "\"managerId\":\"dobias\"," +
                        "\"firstName\":\"Pavla\"," +
                        "\"lastName\":\"Melegova\"," +
                        "\"abbreviation\":\"PME\"," +
                        "\"role\":\"Developer\"," +
                        "\"email\":\"melegova@gmail.com\"," +
                        "\"location\":\"KM1@Mezzanine@KR9\"," +
                        "\"id\":\"melegova\"" +
                    "}," +
                    "{" +
                        "\"employeeGuid\":2," +
                        "\"managerId\":\"dobias\"," +
                        "\"firstName\":\"Ecaterina\"," +
                        "\"lastName\":\"Zubataia\"," +
                        "\"abbreviation\":\"EZU\"," +
                        "\"role\":\"Developer\"," +
                        "\"email\":\"zubataia@cngroup.cz\"," +
                        "\"location\":\"K5@2nd Floor@KR9\"," +
                        "\"id\":\"zubataia\"" +
                    "}," +
                    "{" +
                        "\"employeeGuid\":3," +
                        "\"managerId\":\"dobias\"," +
                        "\"firstName\":\"Michal\"," +
                        "\"lastName\":\"Pipal\"," +
                        "\"abbreviation\":\"MPP\"," +
                        "\"role\":\"Developer\"," +
                        "\"email\":\"pipal@cngroup.cz\"," +
                        "\"location\":\"205@2nd Floor@20\"," +
                        "\"id\":\"pipal\"" +
                    "}" +
                "]"
            ));
  }

    @Test
    public void employeesSingleRequestReturnsVictorCanoJSONData() throws Exception {

        this.mockMvc.perform(get("/employees/Victor Isaias/Cano Becerril")).andDo(print()).andExpect(status().isOk())
            .andExpect(content().json(
                "{" +
                        "\"employeeGuid\":6," +
                        "\"managerId\":\"dobias\"," +
                        "\"firstName\":\"Victor Isaias\"," +
                        "\"lastName\":\"Cano Becerril\"," +
                        "\"abbreviation\":\"VCB\"," +
                        "\"role\":\"Developer\"," +
                        "\"email\":\"cano@cngroup.cz\"," +
                        "\"location\":\"K1@2nd Floor@KR9\"," +
                        "\"id\":\"cano\"" +
                "}"
            ));
  }

    @Test
    public void buildingsRequestReturnsJSONData() throws Exception {

        this.mockMvc.perform(get("/buildings")).andDo(print()).andExpect(status().isOk())
            .andExpect(content().json(
                "[" +
                    "{" +
                    "\"buildingGuid\":0," +
                    "\"buildingId\":\"KR9\"," +
                    "\"type\":\"Office Space\"," +
                    "\"name\":\"Krakovska 9\"," +
                    "\"city\":\"Praha\"," +
                    "\"address\":\"Krakovska 583/9, 110 00\"" +
                    "}," +
                    "{" +
                    "\"buildingGuid\":1," +
                    "\"buildingId\":\"20\"," +
                    "\"type\":\"Headquarters\"," +
                    "\"name\":\"Ve Smeckach 20\"," +
                    "\"city\":\"Praha\"," +
                    "\"address\":\"Ve Smeckach 591/20, 110 00\"" +
                    "}" +
                "]"
            ));
  }

    @Test
    public void buildingsSingleRequestReturnsKrakovskaJSONData() throws Exception {

        this.mockMvc.perform(get("/buildings/KR9")).andDo(print()).andExpect(status().isOk())
            .andExpect(content().json(
                "{" +
                    "\"buildingGuid\":0," +
                    "\"buildingId\":\"KR9\"," +
                    "\"type\":\"Office Space\"," +
                    "\"name\":\"Krakovska 9\"," +
                    "\"city\":\"Praha\"," +
                    "\"address\":\"Krakovska 583/9, 110 00\"" +
                "}"
            ));
  }

    @Test
    public void floorsRequestReturnsJSONData() throws Exception {

        this.mockMvc.perform(get("/floors")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(
                    "[" +
                            "{" +
                            "\"floorId\":0," +
                            "\"floorName\":\"1st Floor\"," +
                            "\"floorplanUrl\":\"/floor201.gif\"," +
                            "\"type\":\"General\"," +
                            "\"buildingId\":\"20\"" +
                            "}," +
                            "{" +
                            "\"floorId\":1," +
                            "\"floorName\":\"2nd Floor\"," +
                            "\"floorplanUrl\":\"/floor202.gif\"," +
                            "\"type\":\"General\"," +
                            "\"buildingId\":\"20\"" +
                            "}," +
                            "{" +
                            "\"floorId\":2," +
                            "\"floorName\":\"Ground\"," +
                            "\"floorplanUrl\":\"/floor200.gif\"," +
                            "\"type\":\"General\"," +
                            "\"buildingId\":\"20\"" +
                            "}," +
                            "{" +
                            "\"floorId\":3," +
                            "\"floorName\":\"Mezzanine\"," +
                            "\"floorplanUrl\":\"/floorKR93.gif\"," +
                            "\"type\":\"General\"," +
                            "\"buildingId\":\"KR9\"" +
                            "}," +
                            "{" +
                            "\"floorId\":4," +
                            "\"floorName\":\"2nd Floor\"," +
                            "\"floorplanUrl\":\"/floorKR92.gif\"," +
                            "\"type\":\"General\"," +
                            "\"buildingId\":\"KR9\"" +
                            "}" +
                        "]"
                ));
    }

    @Test
    public void floorsSingleRequestReturns2ndFloorJSONData() throws Exception {

        this.mockMvc.perform(get("/floors/KR9/2nd Floor")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(
                        "{\"floorId\":4,\"floorName\":\"2nd Floor\",\"floorplanUrl\":\"/floorKR92.gif\",\"type\":\"General\",\"buildingId\":\"KR9\"}"
                ));
    }

    @Test
    public void roomsRequestReturnsJSONData() throws Exception {

        this.mockMvc.perform(get("/rooms")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(
                    "[" +
                        "{" +
                        "\"roomId\":1," +
                        "\"name\":\"K1\"," +
                        "\"type\":\"Development\"," +
                        "\"floorName\":\"2nd Floor\"," +
                        "\"buildingId\":\"KR9\"," +
                        "\"styleTop\":\"-384px\"," +
                        "\"styleLeft\":\"420px\"" +
                        "}," +
                        "{" +
                        "\"roomId\":2," +
                        "\"name\":\"K5\"," +
                        "\"type\":\"Development\"," +
                        "\"floorName\":\"2nd Floor\"," +
                        "\"buildingId\":\"KR9\"," +
                        "\"styleTop\":\"-400px\"," +
                        "\"styleLeft\":\"120px\"" +
                        "}," +
                        "{" +
                        "\"roomId\":3," +
                        "\"name\":\"KM1\"," +
                        "\"type\":\"Development\"," +
                        "\"floorName\":\"Mezzanine\"," +
                        "\"buildingId\":\"KR9\"," +
                        "\"styleTop\":\"-315px\"," +
                        "\"styleLeft\":\"400px\"" +
                        "}," +
                        "{" +
                        "\"roomId\":4," +
                        "\"name\":\"E13\"," +
                        "\"type\":\"Development\"," +
                        "\"floorName\":\"Ground\"," +
                        "\"buildingId\":\"20\"," +
                        "\"styleTop\":\"-355px\"," +
                        "\"styleLeft\":\"630px\"" +
                        "}," +
                        "{" +
                        "\"roomId\":5," +
                        "\"name\":\"205\"," +
                        "\"type\":\"Development\"," +
                        "\"floorName\":\"2nd Floor\"," +
                        "\"buildingId\":\"20\"," +
                        "\"styleTop\":\"-360px\"," +
                        "\"styleLeft\":\"170px\"" +
                        "}," +
                        "{" +
                        "\"roomId\":6," +
                        "\"name\":\"106\"," +
                        "\"type\":\"Development\"," +
                        "\"floorName\":\"1st Floor\"," +
                        "\"buildingId\":\"20\"," +
                        "\"styleTop\":\"-270px\"," +
                        "\"styleLeft\":\"170px\"" +
                        "}" +
                    "]"
                ));
    }

    @Test
    public void roomsSingleRequestReturnsRoom106JSONData() throws Exception {

        this.mockMvc.perform(get("/rooms/20/106")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(
                        "{'roomId':6,'name':'106','type':'Development','floorName':1st Floor,'buildingId':'20','styleTop':'-270px','styleLeft':'170px'}"
                ));
    }

    @Test
    public void updateRoomKM1CoordXY() throws Exception {

        String jsonContent = "{\"roomId\":3,\"name\":\"KM1\",\"type\":\"Development\",\"floorName\":\"Mezzanine\",\"buildingId\":\"KR9\",\"styleTop\":\"-320px\",\"styleLeft\":\"410px\"}";

        this.mockMvc.perform(post("/rooms/update/room").contentType(MediaType.APPLICATION_JSON)
                .content(
                        jsonContent
                ))
                .andExpect(status().isOk());

        Room restoreRoom = new Room();
        restoreRoom.setRoomId(3);
        restoreRoom.setName("KM1");
        restoreRoom.setType("Development");
        restoreRoom.setFloorName("Mezzanine");
        restoreRoom.setBuildingId("KR9");
        restoreRoom.setStyleTop("-315px");
        restoreRoom.setStyleLeft("400px");

        roomRepository.save(restoreRoom);
    }

}
