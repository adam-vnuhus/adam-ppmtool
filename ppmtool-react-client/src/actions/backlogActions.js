import axios from "axios";
import { GET_ERRORS, GET_BACKLOG } from "./types";


export const addProjectTask = (
    backlog_id,
    prokect_task,
    history
) => async dispatch => {

    try {
        await axios.post(`http://localhost:8080/api/backlog/${backlog_id}`, prokect_task);
        history.push(`/projectBoard/${backlog_id}`);
        dispatch({
            type: GET_ERRORS,
            payload: {}
        });
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        });
    }

};

export const getBacklog = backlog_id => async dispatch => {
    try {
        const res = await axios.get(`http://localhost:8080/api/backlog/${backlog_id}`)
        dispatch({
            type: GET_BACKLOG,
            payload: res.data
        });
    } catch (err) {
        dispatch({
            type: GET_ERRORS,
            payload: err.response.data
        });
    }
}