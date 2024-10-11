import React, { useState } from 'react';
import { View, TextInput, Button, Alert, StyleSheet } from 'react-native';
import { useAuth } from '../context/AuthContext';
import { storeData } from '../storage/storage'; 

const Login = ({ navigation }) => {
    const { login } = useAuth();
    const [username, setUsername] = useState('');

    const handleLogin = () => {
        if (username) {
            login(username); 
            storeData('user', { username: username, loggedIn: true }); 
            navigation.navigate('MainScreen'); 
        } else {
            Alert.alert('Por favor, insira um nome de usuário!');
        }
    };

    return (
        <View style={styles.container}>
            <TextInput
                style={styles.input}
                placeholder="Nome de usuário"
                value={username}
                onChangeText={setUsername}
            />
            <Button title="Login" onPress={handleLogin} />
            <Button title="Registrar" onPress={() => navigation.navigate('Register')} />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    input: {
        height: 40,
        borderColor: 'gray',
        borderWidth: 1,
        marginBottom: 12,
        paddingHorizontal: 10,
        width: '80%',
    },
});

export default Login;
