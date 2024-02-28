import {useUser} from "./auth/UserContext";

export function VehicleList() {
    const user = useUser();

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