import {useApi} from "./useApi";
import {useUser} from "../auth/UserContext";

export const useAuthenticatedApi = () => {

    const api = useApi();

    const fetchAuthenticated = async <T>(
        basicAuth: string | undefined,
        url: string,
        method: "GET" | "POST" | "DELETE",
        body?: any,
        headers?: any,
    ): Promise<T> => {
        const authenticatedHeaders = {
            'Authorization': basicAuth,
            ...headers
        }

        return api.fetchUnauthenticated(url, method, body, authenticatedHeaders)
    }

    return {
        fetchAuthenticated
    }
};
