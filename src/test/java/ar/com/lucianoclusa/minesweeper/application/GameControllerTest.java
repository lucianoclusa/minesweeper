package ar.com.lucianoclusa.minesweeper.application;

import ar.com.lucianoclusa.minesweeper.application.model.GameResponse;
import ar.com.lucianoclusa.minesweeper.domain.Board;
import ar.com.lucianoclusa.minesweeper.domain.Game;
import ar.com.lucianoclusa.minesweeper.domain.GameState;
import ar.com.lucianoclusa.minesweeper.domain.Slot;
import ar.com.lucianoclusa.minesweeper.domain.service.GameService;
import ar.com.lucianoclusa.minesweeper.domain.service.UserNotValidForGameException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsSecondArg;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GameService gameService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(gameService);
    }

    @Test
    @DisplayName("Given all correct params When create game is called Then return  200")
    void testResponseOK() throws Exception {
        when(gameService.createGame(eq("testUser"), any())).then(returnsSecondArg());

        String content = getStringFromFile("json/newGame/new_game_body.json");
        String responseAsString = mockMvc.perform(post("/")
                .contentType("application/json")
                .content(content))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        GameResponse returnedGame = objectMapper.readValue(responseAsString, GameResponse.class);

        boolean allSlotsClosed = returnedGame.getSlots().stream().allMatch(slot -> Slot.SlotState.CLOSED.getValue().equals(slot.getValue()));
        assertTrue(allSlotsClosed);
        assertEquals(GameState.NOT_STARTED.name(), returnedGame.getState());
    }

    @Test
    @DisplayName("Given incorrect params When create game is called Then return  400")
    void testNewGameInvalidParams() throws Exception {
        when(gameService.createGame(any(), any())).thenReturn(new Game(new Board(1,1,1)));
        String content = getStringFromFile("json/newGame/invalid_new_game_body.json");

        mockMvc.perform(post("/")
                .contentType("application/json")
                .content(content))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given all correct params When make move is called Then return 200")
    void testMakeMove() throws Exception, UserNotValidForGameException {
        String gameId = "asd-123";
        Game expectedReturnedGame =  new Game(new Board(5, 5, 0));
        expectedReturnedGame.setState(GameState.IN_PROGRESS);
        when(gameService.makeMove(any(), any())).thenReturn(expectedReturnedGame);

        String content = getStringFromFile("json/makeMove/make_move_body.json");

        String responseAsString = mockMvc.perform(post("/" + gameId)
                .contentType("application/json")
                .content(content))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();


        GameResponse returnedGame = objectMapper.readValue(responseAsString, GameResponse.class);
        int arrayExpectedLength = expectedReturnedGame.getBoard().getNumberOfColumns() *  expectedReturnedGame.getBoard().getNumberOfRows();
        assertEquals(arrayExpectedLength, returnedGame.getSlots().size());
        assertEquals(GameState.IN_PROGRESS.name(), returnedGame.getState());
    }

    @Test
    @DisplayName("Given incorrect params When make move is called Then return  400")
    void testMakeMoveInvalidParams() throws Exception, UserNotValidForGameException {
        String gameId = "asd-123";
        when(gameService.makeMove(any(), any())).thenReturn(new Game(new Board(1,1,1)));
        String content = getStringFromFile("json/makeMove/invalid_make_move_body.json");

        mockMvc.perform(post("/" + gameId)
                .contentType("application/json")
                .content(content))
                .andExpect(status().isBadRequest());
    }

    private String getStringFromFile(String path) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(path).getFile());
        return new String(Files.readAllBytes(file.toPath()));
    }
}
