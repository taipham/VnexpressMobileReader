/*
 * Copyright 2013 Chris Banes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ngo.vnexpress.reader.libs.actionbarpulltorefresh.library;

import android.app.Activity;
import android.content.Context;

/**
 * This is used to provide platform and environment specific functionality for
 * the Attacher.
 */
public interface EnvironmentDelegate {

	/**
	 * @return Context which should be used for inflating the header layout
	 */
	public Context getContextForInflater(Activity activity);

}
