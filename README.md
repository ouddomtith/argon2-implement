# User Authentication System (Argon2)
#### Purpose : This authentication system with a focus on transitioning from old hashing to Argon2 hashing for improved password security.
<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
    <li>
      <a href="#">Installation</a>
    </li>
    <li>
      <a href="#">Usage</a>
    </li>
    <li>
      <a href="#">Features</a>
    </li>
</details>


## Installation
#### Manually Downloading JAR Files

Step 1 :Download the Argon2 JAR file from MVN Repository. <br>
Link: https://mvnrepository.com/artifact/de.mkammerer/argon2-jvm/2.1 <br>
Step 2 :Add the JAR file to your project's classpath.


## Usage

### Step 1 : ``Import Argon2Hasher (common)``
- Import the Argon2Hasher class utility into your project
### Step 1 : ``Add hash_type column``
- Add hash type column to your user information table
- Set hash type as your current hash algorithm (optional)
 
### Step 3 : ``Process in login``
- Check hash type of user _(1)
- When the hash type is `argon2` let verify password with Argon2Hasher _(2)
- When the hash type is not `argon2` let verify as normal _(3)
- When verify success, update password by using hashPassword from Argon2Hasher and hash type as `argon2` after(3)  _(4)

### Step 4 : ``Process in register``
- Using hash password with Argon2Hasher
- Set hash type as `argon2`

## Features

<ul>
    <li>Secure password hashing with Argon2</li>
    <li>Automatic re-hashing of your old passwords to Argon2 upon successful login</li>
</ul>


