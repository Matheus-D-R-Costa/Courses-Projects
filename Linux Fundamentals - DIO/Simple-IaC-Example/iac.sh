#!/bin/bash

echo "Criando os diretórios..."
mkdir /public
mkdir /admin
mkdir /sales
mkdir /secretary

echo "Diretórios criados! Criando os Grupos agora..."
groupadd adm
groupadd sales
groupadd secretary

echo "Criando os usuários e atribuindo os grupos a eles..."
useradd -ms /bin/bash -p $(openssl passwd -6 123) -g adm carlos
useradd -ms /bin/bash -p $(openssl passwd -6 123) -g adm maria
useradd -ms /bin/bash -p $(openssl passwd -6 123) -g adm joao_

useradd -ms /bin/bash -p $(openssl passwd -6 123) -g sales debora
useradd -ms /bin/bash -p $(openssl passwd -6 123) -g sales sebastiana
useradd -ms /bin/bash -p $(openssl passwd -6 123) -g sales roberto

useradd -ms /bin/bash -p $(openssl passwd -6 123) -g secretary josefina
useradd -ms /bin/bash -p $(openssl passwd -6 123) -g secretary amanda
useradd -ms /bin/bash -p $(openssl passwd -6 123) -g secretary rogerio

echo "Definindo as permissões agora..."
chown root:adm /admin
chown root:sales /sales
chown root:secretary /secretary

chmod 770 /adm
chmod 770 /sales
chmod 770 /secretary
chmod 777 /public

echo "Pronto, fim do IaC..."
