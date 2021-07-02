/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TenisDetailComponent from '@/entities/tenis/tenis-details.vue';
import TenisClass from '@/entities/tenis/tenis-details.component';
import TenisService from '@/entities/tenis/tenis.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Tenis Management Detail Component', () => {
    let wrapper: Wrapper<TenisClass>;
    let comp: TenisClass;
    let tenisServiceStub: SinonStubbedInstance<TenisService>;

    beforeEach(() => {
      tenisServiceStub = sinon.createStubInstance<TenisService>(TenisService);

      wrapper = shallowMount<TenisClass>(TenisDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { tenisService: () => tenisServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTenis = { id: 123 };
        tenisServiceStub.find.resolves(foundTenis);

        // WHEN
        comp.retrieveTenis(123);
        await comp.$nextTick();

        // THEN
        expect(comp.tenis).toBe(foundTenis);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTenis = { id: 123 };
        tenisServiceStub.find.resolves(foundTenis);

        // WHEN
        comp.beforeRouteEnter({ params: { tenisId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.tenis).toBe(foundTenis);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
