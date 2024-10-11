import React, { useEffect, useState } from 'react';
import { View, Text, Button, StyleSheet } from 'react-native';
import { useAuth } from '../context/AuthContext';
import { getData, removeData } from '../storage/storage'; 

const MainScreen = () => {
    const { user, logout } = useAuth();
    const [userData, setUserData] = useState(null);

    useEffect(() => {
        const loadUserData = async () => {
            const storedUser = await getData('user');
            if (storedUser) {
                setUserData(storedUser);
            }
        };
        loadUserData();
    }, []);

    const handleLogout = () => {
        removeData('user'); 
        logout(); 
    };

    return (
        <View style={styles.container}>
            <Text style={styles.welcomeText}>
                Bem-vindo, {userData ? userData.username : user}!
            </Text>
            <Button title="Logout" onPress={handleLogout} />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    welcomeText: {
        fontSize: 24,
        marginBottom: 20,
    },
});

export default MainScreen;
