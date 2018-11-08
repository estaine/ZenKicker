import Paths from "../dicts/paths";
import {api} from "../config/api";
import {ActionType} from './const';

export const registerGame = async (data) => {
  return await api.post(Paths.Game.RegisterGame, data);
}

export const getLatestGames = () => async (dispatch) => {
  dispatch({type: ActionType.Game.GET_LATEST_GAMES_REQUEST});

  try {
    const result = await api.get(Paths.Game.GetAll, {params: {limit: 5}});
    dispatch({type: ActionType.Game.GET_LATEST_GAMES_SUCCESS, payload: result.data});
  } catch (err) {
    console.warn(err);
    dispatch({type: ActionType.Game.GET_LATEST_GAMES_FAILURE});
  }
}

export const getPlayerGames = (id) => async (dispatch) => {
  dispatch({type: ActionType.Game.GET_PLAYER_GAMES_REQUEST});

  try {
    const result = await api.get(Paths.Game.PlayerGames(id), {params: {limit: 15}});
    dispatch({type: ActionType.Game.GET_PLAYER_GAMES_SUCCESS, payload: result.data});
  } catch (err) {
    console.warn(err);
    dispatch({type: ActionType.Game.GET_PLAYER_GAMES_FAILURE});
  }
}

export const appendToPlayerGames = (gamesLength, id) => async (dispatch) => {
  dispatch({type: ActionType.Game.APPEND_TO_PLAYER_GAMES_REQUEST});

  try {
    const page = {offset: gamesLength, limit: 5};
    const result = await api.get(Paths.Game.PlayerGames(id), {params: page});
    dispatch({type: ActionType.Game.APPEND_TO_PLAYER_GAMES_SUCCESS, payload: result.data});
  } catch (err) {
    console.warn(err);
    dispatch({type: ActionType.Game.APPEND_TO_PLAYER_GAMES_FAILURE});
  }
}

export const getAllGames = () => async (dispatch) => {
  dispatch({type: ActionType.Game.GET_ALL_GAMES_REQUEST});

  try {
    const result = await api.get(Paths.Game.GetAll, {params: {limit: 15}});
    dispatch({type: ActionType.Game.GET_ALL_GAMES_SUCCESS, payload: result.data});
  } catch (err) {
    console.warn(err);
    dispatch({type: ActionType.Game.GET_ALL_GAMES_FAILURE});
  }
}

export const appendToGames = (gamesLength) => async (dispatch) => {
  dispatch({type: ActionType.Game.APPEND_TO_GAMES_REQUEST});

  try {

    const page = {offset: gamesLength, limit: 5};
    const result = await api.get(Paths.Game.GetAll, {params: page});
    dispatch({type: ActionType.Game.APPEND_TO_GAMES_SUCCESS, payload: result.data});
  } catch (err) {
    console.warn(err);
    dispatch({type: ActionType.Game.APPEND_TO_GAMES_FAILURE});
  }
}

export const getGamesCountPerWeek = () => async (dispatch) => {
  dispatch({type: ActionType.Game.GET_GAMES_COUNT_PER_WEEK_REQUEST});

  try {
    const result = await api.get(Paths.Game.GetGamesCount);
    dispatch({type: ActionType.Game.GET_GAMES_COUNT_PER_WEEK_SUCCESS, payload: result.data});
  } catch (err) {
    console.warn(err);
    dispatch({type: ActionType.Game.GET_GAMES_COUNT_PER_WEEK_FAILURE});
  }
}
