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
    public void employeesSingleRequestReturnsVictorCanoJSONData() throws Exception {

        this.mockMvc.perform(get("/employees/Victor Isaias/Cano Becerril")).andDo(print()).andExpect(status().isOk())
            .andExpect(content().json(
                "{" +
                        "\"employeeGuid\":2," +
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
                    "\"buildingGuid\":2," +
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
                    "\"buildingGuid\":2," +
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
                            "\"floorId\":1," +
                            "\"floorName\":\"1st Floor\"," +
                            "\"floorplanUrl\":\"/floor201.gif\"," +
                            "\"type\":\"General\"," +
                            "\"buildingId\":\"20\"" +
                            "}," +
                            "{" +
                            "\"floorId\":2," +
                            "\"floorName\":\"2nd Floor\"," +
                            "\"floorplanUrl\":\"/floor202.gif\"," +
                            "\"type\":\"General\"," +
                            "\"buildingId\":\"20\"" +
                            "}," +
                            "{" +
                            "\"floorId\":3," +
                            "\"floorName\":\"Ground\"," +
                            "\"floorplanUrl\":\"/floor200.gif\"," +
                            "\"type\":\"General\"," +
                            "\"buildingId\":\"20\"" +
                            "}," +
                            "{" +
                            "\"floorId\":4," +
                            "\"floorName\":\"Mezzanine\"," +
                            "\"floorplanUrl\":\"/floorKR93.gif\"," +
                            "\"type\":\"General\"," +
                            "\"buildingId\":\"KR9\"" +
                            "}," +
                            "{" +
                            "\"floorId\":5," +
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
                        "{\"floorId\":5,\"floorName\":\"2nd Floor\",\"floorplanUrl\":\"/floorKR92.gif\",\"type\":\"General\",\"buildingId\":\"KR9\"}"
                ));
    }

    @Test
    public void roomsRequestReturnsJSONData() throws Exception {

        this.mockMvc.perform(get("/rooms")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().json(
                    "[" +
                        "{" +
                        "\"roomId\":3," +
                        "\"name\":\"K1\"," +
                        "\"type\":\"Development\"," +
                        "\"floorName\":\"2nd Floor\"," +
                        "\"buildingId\":\"KR9\"," +
                        "\"styleTop\":\"-384px\"," +
                        "\"styleLeft\":\"420px\"" +
                        "}," +
                        "{" +
                        "\"roomId\":4," +
                        "\"name\":\"K5\"," +
                        "\"type\":\"Development\"," +
                        "\"floorName\":\"2nd Floor\"," +
                        "\"buildingId\":\"KR9\"," +
                        "\"styleTop\":\"-400px\"," +
                        "\"styleLeft\":\"120px\"" +
                        "}," +
                        "{" +
                        "\"roomId\":5," +
                        "\"name\":\"KM1\"," +
                        "\"type\":\"Development\"," +
                        "\"floorName\":\"Mezzanine\"," +
                        "\"buildingId\":\"KR9\"," +
                        "\"styleTop\":\"-320px\"," +
                        "\"styleLeft\":\"410px\"" +
                        "}," +
                        "{" +
                        "\"roomId\":2," +
                        "\"name\":\"E13\"," +
                        "\"type\":\"Development\"," +
                        "\"floorName\":\"Ground\"," +
                        "\"buildingId\":\"20\"," +
                        "\"styleTop\":\"-355px\"," +
                        "\"styleLeft\":\"630px\"" +
                        "}," +
                        "{" +
                        "\"roomId\":6," +
                        "\"name\":\"205\"," +
                        "\"type\":\"Development\"," +
                        "\"floorName\":\"2nd Floor\"," +
                        "\"buildingId\":\"20\"," +
                        "\"styleTop\":\"-360px\"," +
                        "\"styleLeft\":\"170px\"" +
                        "}," +
                        "{" +
                        "\"roomId\":1," +
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
                        "{'roomId':1,'name':'106','type':'Development','floorName':1st Floor,'buildingId':'20','styleTop':'-270px','styleLeft':'170px'}"
                ));
    }
}
