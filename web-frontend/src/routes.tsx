import {Login} from "./login";
import {ReactNode, useEffect} from "react";
import {useUser} from "./auth/UserContext";
import {useNavigate} from "react-router-dom";
import {VehicleList} from "./vehicleList";

function AuthGuard(props: { children: ReactNode }) {
    const user = useUser();
    const navigate = useNavigate();

    useEffect(() => {
        if (!user.basicAuth) {
            navigate('/login');
        }
    }, [user.basicAuth]);

    return (
        <>{props.children}</>
    );
}

const routes = [
    {
        path: "*",
        element: <AuthGuard><VehicleList /></AuthGuard>,
    },
    {
        path: "/login",
        element: <Login />
    }
];

export default routes;