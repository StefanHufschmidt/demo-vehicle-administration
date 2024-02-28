import {createContext, useContext} from "react";

export type UserData = {
    username?: string;
    basicAuth?: string;
    login: (username: string, password: string) => void;
    logout: () => void;
};

export const UserContext = createContext<UserData>({
    login: (username, password) => {},
    logout: () => {}
});

export const useUser = () => useContext(UserContext);
