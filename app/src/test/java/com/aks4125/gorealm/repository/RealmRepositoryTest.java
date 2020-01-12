package com.aks4125.gorealm.repository;

import com.aks4125.gorealm.CustomTestApplication;
import com.aks4125.gorealm.MyApp;
import com.aks4125.gorealm.model.CompanyFilterModel;
import com.aks4125.gorealm.model.CompanyModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.log.RealmLog;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28, application = CustomTestApplication.class)
@PowerMockIgnore({"javax.management.*", "org.powermock.*", "org.mockito.*", "org.robolectric.*", "android.*", "com.aks4125.gorealm.MyApp"})
@SuppressStaticInitializationFor("io.realm.internal.Util")
@PrepareForTest({Realm.class, RealmLog.class, RealmResults.class, RealmQuery.class, RealmObject.class, MyApp.class})
public class RealmRepositoryTest {

    @Rule
    public PowerMockRule rule = new PowerMockRule();
    private Realm mockRealm;
    private RealmRepository repository = new RealmRepository();
    private CompanyModel mCompanyModel;
    private CompanyFilterModel mCompanyFilterModel;

    @Before
    public void setUp() {


        mockStatic(RealmLog.class);
        mockStatic(Realm.class);
        mockStatic(MyApp.class);
        Realm mockRealm = mock(Realm.class);
        mockStatic(RealmResults.class);

        when(Realm.getDefaultInstance()).thenReturn(mockRealm);
        this.mockRealm = mockRealm;
        /*
         * Have to verify the {@link Realm#executeTransaction(Realm.Transaction)} call in a different
         * test because of a problem with Powermock: https://github.com/jayway/powermock/issues/649
         */
        doCallRealMethod().when(mockRealm).executeTransaction(any(Realm.Transaction.class));
        mCompanyModel = new CompanyModel();
        mCompanyModel.setId(1);
        mCompanyModel.setName("mName");
        mCompanyModel.setAddress("mock address");
        mCompanyModel.setEmpCount(1);
        mCompanyModel.setClaps(1);
        mCompanyFilterModel = new CompanyFilterModel();

    }

    @After
    public void tearDown() {
        mockRealm.close();

    }

    @Test
    public void insertOrUpdateCompanyFilter() {
        assertThat(Realm.getDefaultInstance(), is(mockRealm));
        RealmQuery<CompanyFilterModel> mockQuery = mockRealmQuery();
        when(mockQuery.findAll()).thenReturn(mockRealmResults());

        when(mockRealm.where(CompanyFilterModel.class)).thenReturn(mockQuery);
        when(mockQuery.equalTo(anyString(), anyInt())).thenReturn(mockQuery);
        when(mockQuery.equalTo(anyString(), anyString())).thenReturn(mockQuery);

        when(mockQuery.findFirst()).thenReturn(mCompanyFilterModel);
        doNothing().when(mockRealm).insertOrUpdate(mCompanyFilterModel);


        repository.insertOrUpdateCompanyFilter(mCompanyFilterModel);
        when(mockQuery.findFirst()).thenReturn(null);
        repository.insertOrUpdateCompanyFilter(mCompanyFilterModel);

        verify(mockRealm, times(6)).executeTransaction(Mockito.any(Realm.Transaction.class));
        verify(mockRealm, times(2)).close(); // check database is close called or not
    }

    @Test
    public void insertOrUpdateCompanyList() {
        assertThat(Realm.getDefaultInstance(), is(mockRealm));
        List<CompanyModel> mList = Collections.singletonList(mCompanyModel);
        repository.insertOrUpdateCompanyList(mList);
        verify(mockRealm, times(1)).close();

    }

    @Test
    public void isCompanyListEmpty() {
        assertThat(Realm.getDefaultInstance(), is(mockRealm));
        RealmQuery<CompanyModel> mockQuery = mockRealmQuery();
        RealmResults<CompanyModel> mockRealmResults = mockRealmResults();

        when(mockRealm.where(CompanyModel.class)).thenReturn(mockQuery);
        when(mockQuery.findAll()).thenReturn(mockRealmResults);
        when(mockRealmResults.isEmpty()).thenReturn(false);


        repository.isCompanyListEmpty();
        verify(mockRealm, times(1)).close();


    }

    @Test
    public void getCompanyList() {
        assertThat(Realm.getDefaultInstance(), is(mockRealm));
        assertThat(Realm.getDefaultInstance(), is(mockRealm));
        RealmQuery<CompanyModel> mockQuery = mockRealmQuery();
        RealmResults<CompanyModel> companyModelRealmResults = mockRealmResults();

        when(mockRealm.where(CompanyModel.class)).thenReturn(mockQuery);
        when(mockQuery.findAll()).thenReturn(companyModelRealmResults);
        when(companyModelRealmResults.isEmpty()).thenReturn(false);
        when(mockRealm.copyFromRealm(companyModelRealmResults)).thenReturn(companyModelRealmResults);

        repository.getCompanyList();
        verify(mockRealm, times(1)).close();

    }

    @Test
    public void getFilteredList() {
        assertThat(Realm.getDefaultInstance(), is(mockRealm));
        CompanyFilterModel mCompanyFilterModel = new CompanyFilterModel();


        RealmQuery<CompanyModel> mockQuery = mockRealmQuery();
        RealmResults<CompanyModel> companyModelRealmResults = mockRealmResults();

        when(mockRealm.where(CompanyModel.class)).thenReturn(mockQuery);
        when(mockQuery.equalTo(anyString(), anyInt())).thenReturn(mockQuery);
        when(mockQuery.equalTo(anyString(), anyString())).thenReturn(mockQuery);

        when(mockQuery.findFirst()).thenReturn(mCompanyModel);

        Sort sort = mock(Sort.class);
        when(sort.getValue()).thenReturn(true);

        when(mockQuery.equalTo(anyString(), anyBoolean())).thenReturn(mockQuery);
        when(mockQuery.sort(anyString(), any())).thenReturn(mockQuery);

        when(mockQuery.findAll()).thenReturn(companyModelRealmResults);


        when(mockRealm.copyFromRealm(mCompanyModel)).thenReturn(mCompanyModel);
        doNothing().when(mockRealm).insertOrUpdate(mCompanyModel);


        repository.getFilteredList(mCompanyFilterModel);
        mCompanyFilterModel.setGroupId(1); // filter by id test
        repository.getFilteredList(mCompanyFilterModel);
        mCompanyFilterModel.setGroupId(3); // filter by name test
        repository.getFilteredList(mCompanyFilterModel);

        verify(mockRealm, times(3)).close(); // check database is close called or not

    }

    @Test
    public void insertOrUpdateCompanyObject() {
        assertThat(Realm.getDefaultInstance(), is(mockRealm));
        repository.insertOrUpdateCompanyObject(mCompanyModel);
        verify(mockRealm, times(1)).close(); // check database is close called or not

    }

    @Test
    public void getCompanyById() {
        assertThat(Realm.getDefaultInstance(), is(mockRealm));
        RealmQuery<CompanyModel> mockQuery = mockRealmQuery();
        when(mockRealm.where(CompanyModel.class)).thenReturn(mockQuery);
        when(mockQuery.equalTo(anyString(), anyInt())).thenReturn(mockQuery);
        when(mockQuery.findFirst()).thenReturn(mCompanyModel);
        when(mockRealm.copyFromRealm(mCompanyModel)).thenReturn(mCompanyModel);

        repository.getCompanyById(1);

    }

    @SuppressWarnings("unchecked")
    private <T extends RealmObject> RealmQuery<T> mockRealmQuery() {
        return mock(RealmQuery.class);
    }

    @SuppressWarnings("unchecked")
    private <T extends RealmObject> RealmResults<T> mockRealmResults() {
        return mock(RealmResults.class);
    }
}