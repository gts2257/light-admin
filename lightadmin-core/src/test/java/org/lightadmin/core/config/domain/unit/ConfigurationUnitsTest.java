package org.lightadmin.core.config.domain.unit;

import org.junit.Test;
import org.lightadmin.core.config.annotation.Administration;
import org.lightadmin.core.config.bootstrap.parsing.configuration.DomainConfigurationUnitType;
import org.lightadmin.core.config.domain.configuration.EntityMetadataConfigurationUnit;

import java.util.Set;

import static com.google.common.collect.Sets.newLinkedHashSet;
import static org.junit.Assert.*;
import static org.lightadmin.core.config.bootstrap.parsing.configuration.DomainConfigurationUnitType.*;
import static org.lightadmin.core.test.util.ConfigurationUnitsUtils.*;

public class ConfigurationUnitsTest {

	@Test( expected = IllegalArgumentException.class )
	public void nullDomainTypeNotAllowed() throws Exception {
		configurationUnits( null );
	}

	@Test
	public void correctDomainTypeReturned() throws Exception {
		assertEquals( configurationUnits( DomainTypeConfiguration.class ).getDomainType(), DomainType.class );
	}

	@Test
	public void existingUnitFound() throws Exception {
		final ConfigurationUnits testee = configurationUnits( DomainTypeConfiguration.class, configurationUnitFor( CONFIGURATION, EntityMetadataConfigurationUnit.class ) );

		assertNotNull( testee.getEntityConfiguration() );
	}

	@Test
	public void nullReturnedForMissingConfigurationUnit() throws Exception {
		final ConfigurationUnits testee = configurationUnits( DomainTypeConfiguration.class, configurationUnitFor( FILTERS ) );

		assertNull( testee.getScreenContext() );
	}

	@Test
	public void unitsOrderTheSameAsInitial() throws Exception {
		final ConfigurationUnits testee = configurationUnits( DomainTypeConfiguration.class, configurationUnitsFor( FILTERS, CONFIGURATION, SCOPES ) );

		assertUnitsOrderEqual( testee, new DomainConfigurationUnitType[] {FILTERS, CONFIGURATION, SCOPES} );
	}

	private void assertUnitsOrderEqual( final ConfigurationUnits testee, final DomainConfigurationUnitType[] expectedOrderedUnitTypes ) {
		Set<DomainConfigurationUnitType> actualUnitTypes = newLinkedHashSet();
		for ( ConfigurationUnit configurationUnit : testee ) {
			actualUnitTypes.add( configurationUnit.getDomainConfigurationUnitType() );
		}

		assertArrayEquals( actualUnitTypes.toArray(), expectedOrderedUnitTypes );
	}

	private static class DomainType {

	}

	@Administration( DomainType.class )
	private static class DomainTypeConfiguration {

	}
}