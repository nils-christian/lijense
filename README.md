# liJense

liJense is a small open-source framework to use licenses in your Java application. It uses RSA-based keys to sign and validate your license files.

Please note that I am not a security expert. I provide you with this library without any liability. If you are a security expert and see any design or implementation flaws, please do not hesitate to contact me.

## How do I use it?
In order to use liJense in your project, simply add the Maven dependency to your pom.xml. The library requires Java 8 and works with the JREs from Oracle and from OpenJDK. Other JREs might work as well, but I performed no such tests.

	<dependency>
		<groupId>de.rhocas.lijense</groupId>
		<artifactId>lijense</artifactId>
		<version>1.0.0</version>
	</dependency>

The following snippet shows a simple example. It creates a new RSA key pair, creates a license file and loads it afterwards.

	// Generate a new key pair
	KeyPair keyPair = KeyUtil.generateNewKeyPair( );
	PrivateKey privateKey = keyPair.getPrivate( );
	PublicKey publicKey = keyPair.getPublic( );
	
	// Create and sign the license file
	ModifiableLicense modifiableLicense = new ModifiableLicense( );
	modifiableLicense.setProperty( "myFeature.active", "true" );
	LicenseUtil.saveLicenseFile( modifiableLicense, privateKey, new File( "myLicense.dat" ) );
	
	// Load and verify the license file (without fingerprint for the public key)
	UnmodifiableLicense unmodifiableLicense = LicenseUtil.loadLicenseFile( publicKey, new File( "myLicense.dat" ), Optional.<byte[]>empty( ) );
	System.out.println( unmodifiableLicense.getValue( "myFeature.active" ) );

## How does it work?

A license file within liJense is basically an archive file containing two files. The first one is a common Java properties file saved in XML format - a file containing arbitrary key/value-pairs. The second file is the signature file for the properties file. While creating such a license file, liJense generates this signature with a 4096 bit strong private RSA key. The signature is calculated with SHA-512. The validity of the signature can later be checked in your application by using the corresponding public RSA key (which is shipped with your application). If the signature is not valid, the license file has been tampered with. In order to avoid that the public key in your application is simply replaced, the fingerprint of the public key can also be checked in the source code (which is the SHA-512 hash of the public key). 

The 4096 bit RSA keys can be generated by liJense as well. For this purpose, a peuseo random generator based on SHA-1 is used. 

All used algorithms are already shipped with the Java runtime environment. No further libraries are required and I did not try to reimplement any of the cryptographic algorithms.

## License

liJense itself is licensed under the MIT license. You can find a copy of it in the LICENSE file in the root folder of the project.
