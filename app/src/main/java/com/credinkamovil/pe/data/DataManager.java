package com.credinkamovil.pe.data;

import com.credinkamovil.pe.data.local.db.DbHelper;
import com.credinkamovil.pe.data.local.prefs.PreferenceHelper;
import com.credinkamovil.pe.data.remote.ApiHelper;

public interface DataManager extends DbHelper, PreferenceHelper, ApiHelper {
}
