/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.announcements.uad.anonymizer.test;

import com.liferay.announcements.kernel.model.AnnouncementsFlag;
import com.liferay.announcements.kernel.service.AnnouncementsFlagLocalService;
import com.liferay.announcements.uad.constants.AnnouncementsUADConstants;
import com.liferay.announcements.uad.test.AnnouncementsFlagUADEntityTestHelper;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.test.util.BaseUADEntityAnonymizerTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Noah Sherrill
 */
@RunWith(Arquillian.class)
public class AnnouncementsFlagUADEntityAnonymizerTest
	extends BaseUADEntityAnonymizerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	protected Object addDataObject(long userId) throws Exception {
		AnnouncementsFlag announcementsFlag =
			_announcementsFlagUADEntityTestHelper.addAnnouncementsFlag(userId);

		_announcementsFlags.add(announcementsFlag);

		return announcementsFlag;
	}

	@Override
	protected long getDataObjectId(Object dataObject) {
		AnnouncementsFlag announcementsFlag = (AnnouncementsFlag)dataObject;

		return announcementsFlag.getFlagId();
	}

	@Override
	protected String getUADRegistryKey() {
		return AnnouncementsUADConstants.ANNOUNCEMENTS_FLAG;
	}

	@Override
	protected boolean isDataObjectAutoAnonymized(long dataObjectId, User user)
		throws Exception {

		AnnouncementsFlag announcementsFlag =
			_announcementsFlagLocalService.getAnnouncementsFlag(dataObjectId);

		if (user.getUserId() != announcementsFlag.getUserId()) {
			return true;
		}

		return false;
	}

	@Override
	protected boolean isDataObjectDeleted(long dataObjectId) {
		if (_announcementsFlagLocalService.fetchAnnouncementsFlag(
				dataObjectId) == null) {

			return true;
		}

		return false;
	}

	@Inject
	private AnnouncementsFlagLocalService _announcementsFlagLocalService;

	@DeleteAfterTestRun
	private final List<AnnouncementsFlag> _announcementsFlags =
		new ArrayList<>();

	@Inject
	private AnnouncementsFlagUADEntityTestHelper
		_announcementsFlagUADEntityTestHelper;

}