import {Login} from "./pages/login";
import {ReactNode, useEffect} from "react";
import {useUser} from "./auth/UserContext";
import {useNavigate} from "react-router-dom";
import {VehicleList} from "./pages/vehicleList";
import {Register} from "./pages/register";

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
    },
    {
        path: "/register",
        element: <Register />
    }
];

export default routes;