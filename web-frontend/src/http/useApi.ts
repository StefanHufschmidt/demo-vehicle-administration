export const useApi = () => {
    const controller = new AbortController();
    // const baseUrl = `${window.location.protocol}//${window.location.host}/api`;
    const baseUrl = 'http://localhost:8080/api'; // TODO: Use API from above after testing
    const fetchUnauthenticated = async <T>(
        url: string,
        method: 'GET' | 'POST' | 'DELETE',
        body?: any,
        headers?: any
    ): Promise<T> => {
        const response = await fetch(`${baseUrl}${url}`, {
            body: body ? JSON.stringify(body) : body,
            method: method,
            headers: {...headers},
            signal: controller.signal,
        });
        const statusCode = response.status;
        if (statusCode >= 400) {
            throw Error(`${baseUrl}${url} returned ${statusCode}`);
        }

        const bodyString = await response.text();
        return bodyString.length > 0 ? JSON.parse(bodyString) : bodyString;
    };

    return {
        fetchUnauthenticated
    };
};