import {useUser} from "../auth/UserContext";
import {useAuthenticatedApi} from "../http/useAuthenticatedApi";
import {useEffect} from "react";

export function VehicleList() {
    const user = useUser();
    const authenticatedApi = useAuthenticatedApi();

    useEffect(() => {
        if (user.basicAuth) {
            authenticatedApi.fetchAuthenticated(user.basicAuth, '/web/ping', 'GET').then(() => {
                console.log("Authenticated reuqest worked!");
            });
        }
    }, [user.basicAuth]);

    return (
        <div>
            <p>Welcome {user.username}</p>
            <ul>
                <li>Something</li>
            </ul>
            <button type="button" onClick={user.logout}>Logout</button>
        </div>
    );
}